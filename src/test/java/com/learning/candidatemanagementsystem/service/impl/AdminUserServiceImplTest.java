package com.learning.candidatemanagementsystem.service.impl;


import com.learning.candidatemanagementsystem.dto.CandidateDto;
import com.learning.candidatemanagementsystem.dto.UserDto;
import com.learning.candidatemanagementsystem.model.*;
import com.learning.candidatemanagementsystem.repository.CandidateRepository;
import com.learning.candidatemanagementsystem.repository.RoleRepository;
import com.learning.candidatemanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static com.learning.candidatemanagementsystem.constant.RoleType.CANDIDATE;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("AdminUserServiceImpl - Unit Tests")
@ExtendWith(MockitoExtension.class)
class AdminUserServiceImplTest {

    @InjectMocks
    private AdminUserServiceImpl adminUserService;

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private CandidateRepository candidateRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @Captor private ArgumentCaptor<User> userCaptor;


    @Nested
    @DisplayName("Create User")
    class CreateUserTests {

        @Test
        @DisplayName("Should create user with candidate role and save candidate entity")
        void shouldCreateUserWithCandidateRole() {
            UserDto dto = new UserDto();
            dto.setUsername("john");
            dto.setPassword("pass");
            dto.setRoles(Set.of("CANDIDATE"));
            dto.setCandidateDto(new CandidateDto(2L, "testUser", "john@example.com", "resume.pdf"));

            Role candidateRole = new Role(CANDIDATE.name());

            when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
            when(roleRepository.findByName(CANDIDATE.name())).thenReturn(Optional.of(candidateRole));
            when(passwordEncoder.encode("pass")).thenReturn("encodedPass");

            boolean result = adminUserService.createUser(dto);

            assertThat(result).isTrue();

            verify(userRepository, times(1)).save(userCaptor.capture());

            User savedUser = userCaptor.getValue();
            assertThat(savedUser.getUsername()).isEqualTo("john");
            assertThat(savedUser.getPassword()).isEqualTo("encodedPass");

            assertThat(savedUser.getUserRoles()).hasSize(1);
            assertThat(savedUser.getUserRoles().iterator().next().getRole().getName()).isEqualTo("CANDIDATE");

            verify(candidateRepository, times(1)).save(any(Candidate.class));
        }
    }
}
