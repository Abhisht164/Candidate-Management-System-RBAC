package com.learning.candidatemanagementsystem.service;

import com.learning.candidatemanagementsystem.dto.CandidateDto;
import com.learning.candidatemanagementsystem.dto.CandidateRequestDto;
import com.learning.candidatemanagementsystem.dto.CandidateResponseDto;


import java.util.List;

public interface IRecruiterCandidateService {

    boolean addCandidate(CandidateRequestDto request);

    List<CandidateResponseDto> fetchAllCandidates();

    boolean updateCandidate(Long candidateId, CandidateDto request);

    CandidateResponseDto fetchCandidate(Long candidateId);
}
