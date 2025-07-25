package com.learning.candidatemanagementsystem.service.impl;

import com.learning.candidatemanagementsystem.dto.CandidateDto;
import com.learning.candidatemanagementsystem.dto.CandidateRequestDto;
import com.learning.candidatemanagementsystem.dto.CandidateResponseDto;
import com.learning.candidatemanagementsystem.exception.ResourceNotFoundException;
import com.learning.candidatemanagementsystem.mapper.CandidateMapper;
import com.learning.candidatemanagementsystem.model.Candidate;
import com.learning.candidatemanagementsystem.model.Role;
import com.learning.candidatemanagementsystem.model.User;
import com.learning.candidatemanagementsystem.model.UserRole;
import com.learning.candidatemanagementsystem.repository.CandidateRepository;
import com.learning.candidatemanagementsystem.repository.RoleRepository;
import com.learning.candidatemanagementsystem.repository.UserRepository;
import com.learning.candidatemanagementsystem.service.IRecruiterCandidateService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RecruiterCandidateServiceImpl implements IRecruiterCandidateService {

    private CandidateRepository candidateRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Transactional
    public boolean addCandidate(CandidateRequestDto request) {
        log.info("Adding candidate for userId: {}", request.getUserId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    log.warn("User not found with ID: {}", request.getUserId());
                    return new ResourceNotFoundException("User", "id", String.valueOf(request.getUserId()));
                });
        boolean hasCandidateRole = user.getUserRoles().stream()
                .anyMatch(ur -> ur.getRole().getName().equalsIgnoreCase("CANDIDATE"));

        if (!hasCandidateRole) {
            Role candidateRole = roleRepository.findByName("CANDIDATE")
                    .orElseThrow(() -> {
                        log.error("Candidate role not found in database");
                        return new RuntimeException("Role not found: CANDIDATE");
                    });

            user.getUserRoles().add(new UserRole(user, candidateRole));
            userRepository.save(user);
            log.info("Candidate role assigned to user ID: {}", user.getId());
        }
        Candidate candidate = CandidateMapper.mapToRequestCandidate(request, new Candidate());
        candidate.setUser(user);
        candidateRepository.save(candidate);

        log.info("Candidate added successfully for userId: {}", user.getId());
        return true;
    }

    public List<CandidateResponseDto> fetchAllCandidates() {
        log.info("Fetching all candidates");
        List<Candidate> all = candidateRepository.findAll();
        log.info("Total candidates found: {}", all.size());
        return CandidateMapper.mapToCandidateResponseDtoList(all);
    }
    @Transactional
    @Override
    public boolean updateCandidate(Long candidateId, CandidateDto request) {
        log.info("Updating candidate with ID: {}", candidateId);
        boolean isUpdated = false;
        if (request != null) {
            Candidate candidate = candidateRepository.findById(candidateId)
                    .orElseThrow(() -> {
                        log.warn("Candidate not found with ID: {}", candidateId);
                        return new ResourceNotFoundException("Candidate", "id", String.valueOf(candidateId));
                    });
            candidate.setFullName(request.getFullName());
            candidate.setEmail(request.getEmail());
            candidate.setResume(request.getResume());

            candidateRepository.save(candidate);
            log.info("Candidate updated successfully with ID: {}", candidateId);

            isUpdated = true;
        }
        return isUpdated;
    }

    public CandidateResponseDto fetchCandidate(Long candidateId) {
        log.info("Fetching candidate with ID: {}", candidateId);

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> {
                    log.warn("Candidate not found with ID: {}", candidateId);
                    return new ResourceNotFoundException("Candidate", "id", String.valueOf(candidateId));
                });

        return CandidateMapper.mapToCandidateResponseDto(candidate, new CandidateResponseDto());
    }

}
