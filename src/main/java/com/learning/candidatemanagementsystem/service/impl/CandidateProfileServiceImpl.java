package com.learning.candidatemanagementsystem.service.impl;

import com.learning.candidatemanagementsystem.dto.CandidateDto;
import com.learning.candidatemanagementsystem.dto.CandidateResponseDto;
import com.learning.candidatemanagementsystem.exception.ResourceNotFoundException;
import com.learning.candidatemanagementsystem.mapper.CandidateMapper;
import com.learning.candidatemanagementsystem.model.Candidate;
import com.learning.candidatemanagementsystem.model.User;
import com.learning.candidatemanagementsystem.repository.CandidateRepository;
import com.learning.candidatemanagementsystem.repository.UserRepository;
import com.learning.candidatemanagementsystem.service.ICandidateProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@SuppressWarnings("squid:S1192")
public class CandidateProfileServiceImpl implements ICandidateProfileService {

    private CandidateRepository candidateRepository;

    private UserRepository userRepository;

    @Override
    public CandidateResponseDto viewProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Fetching profile for candidate with username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found with username: {}", username);
                    return new ResourceNotFoundException("User", "username", username);
                });

        Candidate candidate = candidateRepository.findByUser(user)
                .orElseThrow(() -> {
                    log.warn("Candidate not found for user ID: {}", user.getId());
                    return new ResourceNotFoundException("Candidate", "userId", String.valueOf(user.getId()));
                });

        log.info("Candidate profile fetched successfully for username: {}", username);
        return CandidateMapper.mapToCandidateResponseDto(candidate, new CandidateResponseDto());
    }

    @Override
    public boolean updateProfile(CandidateDto request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Received request to update candidate profile for username: {}", username);

        if (request == null) {
            log.warn("Update request is null for candidate: {}", username);
            throw new IllegalArgumentException("Candidate update request cannot be null");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found with username: {}", username);
                    return new ResourceNotFoundException("User", "username", username);
                });

        Candidate candidate = candidateRepository.findByUser(user)
                .orElseThrow(() -> {
                    log.warn("Candidate not found for user ID: {}", user.getId());
                    return new ResourceNotFoundException("Candidate", "userId", String.valueOf(user.getId()));
                });

        candidate.setFullName(request.getFullName());
        candidate.setEmail(request.getEmail());
        candidate.setResume(request.getResume());
        candidateRepository.save(candidate);

        log.info("Candidate profile updated successfully for username: {}", username);
        return true;
    }
}
