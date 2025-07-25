package com.learning.candidatemanagementsystem.mapper;

import com.learning.candidatemanagementsystem.dto.*;
import com.learning.candidatemanagementsystem.model.Candidate;

import java.util.List;

public class CandidateMapper {

    private CandidateMapper() {
        throw new UnsupportedOperationException("Utility class");
    }
    public static CandidateDto mapToCandidateDto(Candidate candidate, CandidateDto candidateDto) {
        candidateDto.setFullName(candidate.getFullName());
        candidateDto.setEmail(candidate.getEmail());
        candidateDto.setResume(candidate.getResume());
        return candidateDto;
    }
    public static CandidateResponseDto mapToCandidateResponseDto(Candidate candidate, CandidateResponseDto candidateResponseDto) {
        candidateResponseDto.setFullName(candidate.getFullName());
        candidateResponseDto.setEmail(candidate.getEmail());
        candidateResponseDto.setResume(candidate.getResume());
        candidateResponseDto.setCandidateId(candidate.getId());
        candidateResponseDto.setUserId(candidate.getUser().getId());
        return candidateResponseDto;
    }
    public static Candidate mapToCandidate(CandidateDto candidateDto, Candidate candidate) {
        candidate.setFullName(candidateDto.getFullName());
        candidate.setEmail(candidateDto.getEmail());
        candidate.setResume(candidateDto.getResume());
        return candidate;
    }
    public static List<CandidateResponseDto> mapToCandidateResponseDtoList(List<Candidate> candidates) {
        return candidates.stream()
                .map(c -> new CandidateResponseDto(c.getId(),c.getUser().getId(), c.getFullName(), c.getEmail(), c.getResume()))
                .toList();
    }

    public static GenericResponseDto mapToCandidateUpdateResponseDto(Candidate updatedCandidate) {
        GenericResponseDto candidateGenericResponseDto = new GenericResponseDto();
        candidateGenericResponseDto.setMessage(updatedCandidate.getEmail());
        candidateGenericResponseDto.setStatus(updatedCandidate.getFullName());
        candidateGenericResponseDto.setMessage(updatedCandidate.getResume());
        return candidateGenericResponseDto;
    }

    public static Candidate mapToRequestCandidate(CandidateRequestDto request, Candidate candidate) {
        candidate.setFullName(request.getFullName());
        candidate.setEmail(request.getEmail());
        candidate.setResume(request.getResume());
        return candidate;
    }
}
