package com.learning.candidatemanagementsystem.constant;

import com.learning.candidatemanagementsystem.model.Role;
import com.learning.candidatemanagementsystem.model.User;
import com.learning.candidatemanagementsystem.model.UserRole;
import com.learning.candidatemanagementsystem.model.keys.UserRoleId;
import com.learning.candidatemanagementsystem.repository.RoleRepository;
import com.learning.candidatemanagementsystem.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.learning.candidatemanagementsystem.constant.RoleType.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${root.user.name}")
    private String userName;

    @Value(("${root.user.password}"))
    private String password;

    @PostConstruct
    @Transactional
    public void init() {
        if (userRepository.count() == 0) {
            log.info("No users found in database. Initializing default roles and admin user.");
            // Create and save roles first
            Role adminRole = roleRepository.findByName(ADMIN.name())
                    .orElseGet(() -> roleRepository.save(new Role(ADMIN.name())));

            roleRepository.save(new Role(RECRUITER.name()));

           roleRepository.saveAndFlush(new Role(CANDIDATE.name()));

            // Create and save admin user
            User admin = new User();
            admin.setUsername(userName);
            admin.setPassword(passwordEncoder.encode(password));
            admin = userRepository.save(admin);
            log.info("Admin user '{}' created.", userName);

            UserRole adminUserRole = new UserRole();

            adminUserRole.setRole(adminRole);
            adminUserRole.setUser(admin);

            UserRoleId adminUserRoleId = new UserRoleId();
            adminUserRoleId.setUserId(admin.getId());
            adminUserRoleId.setRoleId(adminRole.getId());
            adminUserRole.setId(adminUserRoleId);

            admin.getUserRoles().add(adminUserRole);

            // Save the user (cascade should handle the userRole)
            userRepository.saveAndFlush(admin);

            log.info("Admin user '{}' assigned role '{}'. Initialization complete.", userName, ADMIN);
        } else {
            log.info("Users already exist in the database. Skipping initialization.");
        }
    }
}

