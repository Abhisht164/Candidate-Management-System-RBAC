package com.learning.candidatemanagementsystem.service;

import com.learning.candidatemanagementsystem.dto.CandidateDto;
import com.learning.candidatemanagementsystem.dto.CandidateResponseDto;

public interface ICandidateProfileService {

    CandidateResponseDto viewProfile();

    boolean updateProfile(CandidateDto request);
}
