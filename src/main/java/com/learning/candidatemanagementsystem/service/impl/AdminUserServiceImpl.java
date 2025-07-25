package com.learning.candidatemanagementsystem.service.impl;

import com.learning.candidatemanagementsystem.dto.UserDto;
import com.learning.candidatemanagementsystem.dto.UserResponseDto;
import com.learning.candidatemanagementsystem.exception.ResourceNotFoundException;
import com.learning.candidatemanagementsystem.exception.UserAlreadyExistException;
import com.learning.candidatemanagementsystem.mapper.UserMapper;
import com.learning.candidatemanagementsystem.model.Candidate;
import com.learning.candidatemanagementsystem.model.Role;
import com.learning.candidatemanagementsystem.model.User;
import com.learning.candidatemanagementsystem.model.UserRole;
import com.learning.candidatemanagementsystem.repository.CandidateRepository;
import com.learning.candidatemanagementsystem.repository.RoleRepository;
import com.learning.candidatemanagementsystem.repository.UserRepository;
import com.learning.candidatemanagementsystem.service.IAdminUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AdminUserServiceImpl implements IAdminUserService {

    private UserRepository userRepository;
    private  RoleRepository roleRepository;
    private CandidateRepository candidateRepository;
    private PasswordEncoder passwordEncoder;

    public List<UserResponseDto> fetchAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            log.warn("No users found in the database");
            throw new ResourceNotFoundException("User", "all", "no users found");
        }
        log.info("Successfully fetched {} users", users.size());
        return UserMapper.mapToUserResponseDtoList(users);
    }
    @Transactional
            public boolean createUser(UserDto userRequest) {
                if (userRequest != null) {
                    if (userRepository.findByUsername(userRequest.getUsername()).isPresent())
                    {
                        log.warn("User with username '{}' already exists", userRequest.getUsername());
                        throw new UserAlreadyExistException("User", "id", userRequest.getUsername());
                    }

                        User user = UserMapper.mapToUser(userRequest, new User());
                        user.setPassword(passwordEncoder.encode(user.getPassword()));
                    log.info("User '{}' created successfully with ID: {}", user.getUsername(), user.getId());
                        Set<UserRole> userRoles = userRequest.getRoles().stream()
                                .map(roleName -> {
                                    Role role=roleRepository.findByName(roleName).orElseThrow(()->{
                                        log.error("Role '{}' not found", roleName);
                                       return  new ResourceNotFoundException("Role", "name", roleName);
                                    });
                                    return new UserRole(user, role);
                                })
                                .collect(Collectors.toSet());
                        user.setUserRoles(userRoles);
                        userRepository.save(user);
                    log.info("Assigned roles {} to user '{}'", userRequest.getRoles(), user.getUsername());

                    if (userRequest.getRoles().contains("CANDIDATE") &&  userRequest.getCandidateDto()!=null) {
                        Candidate candidate = new Candidate();
                        candidate.setUser(user);
                        candidate.setFullName(userRequest.getCandidateDto().getFullName());
                        candidate.setEmail(userRequest.getCandidateDto().getEmail());
                        candidate.setResume(userRequest.getCandidateDto().getResume());
                        candidateRepository.save(candidate);
                        log.info("Candidate profile created for user '{}'", user.getUsername());
                    }
                }
                else {
                    log.error("User request is null");
                    throw new IllegalArgumentException("User cannot be null");
                }
                return true;
            }
            @Transactional
            public boolean updateUser(Long userId, UserDto userRequest) {
                log.info("Received request to update user with ID: {}", userId);

                boolean isUpdated = false;

                if (userRequest != null) {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User", "id", String.valueOf(userId)));

                    Optional<User> userWithSameUsername = userRepository.findByUsername(userRequest.getUsername());
                    if (userWithSameUsername.isPresent() && !userWithSameUsername.get().getId().equals(userId)) {
                        throw new UserAlreadyExistException("User", "username", userRequest.getUsername());
                    }

                    user.setUsername(userRequest.getUsername());
                    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

                    if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
                        user.getUserRoles().clear();
                        userRepository.flush();

                        Set<UserRole> newUserRoles = userRequest.getRoles().stream()
                                .map(roleName -> {
                                    Role role = roleRepository.findByName(roleName)
                                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                                    return new UserRole(user, role);
                                })
                                .collect(Collectors.toSet());

                        user.getUserRoles().addAll(newUserRoles);
                    }

                    userRepository.save(user);
                    log.info("User '{}' updated successfully", user.getUsername());

                    // Handle Candidate entity on role changes
                    boolean hasCandidateRole = user.getUserRoles().stream()
                            .anyMatch(ur -> ur.getRole().getName().equals("CANDIDATE"));

                    Optional<Candidate> candidateOpt = candidateRepository.findByUser(user);

                    if (hasCandidateRole && candidateOpt.isEmpty()&&  userRequest.getCandidateDto()!=null) {
                        Candidate candidate = new Candidate();
                        candidate.setUser(user);
                        candidate.setFullName(user.getUsername());
                        candidate.setEmail(userRequest.getCandidateDto().getEmail());
                        candidate.setResume(userRequest.getCandidateDto().getResume());
                        candidateRepository.save(candidate);
                        log.info("Candidate profile created for updated user '{}'", user.getUsername());
                    } else if (!hasCandidateRole && candidateOpt.isPresent()) {
                        candidateRepository.delete(candidateOpt.get());
                        log.info("Candidate profile deleted for user '{}'", user.getUsername());
                    }

                    isUpdated = true;
                }

                return isUpdated;
            }
}
