package com.learning.candidatemanagementsystem.controller;

import com.learning.candidatemanagementsystem.constant.UserConstants;
import com.learning.candidatemanagementsystem.dto.*;
import com.learning.candidatemanagementsystem.service.IRecruiterCandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Recruiter Candidate Handler",
        description = "APIs for recruiters to create, update, and fetch candidate details"
)
@RestController
@RequestMapping(value = "/api/recruiter/candidates", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class RecruiterCandidateController {

    private final IRecruiterCandidateService candidateService;

    @Operation(summary = "Add Candidate", description = "Add a new candidate")
    @ApiResponse(
            responseCode = "201",
            description = "Candidate created successfully",
            content = @Content(schema = @Schema(implementation = GenericResponseDto.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    @PostMapping
    public ResponseEntity<GenericResponseDto> addCandidate(@Valid @RequestBody CandidateRequestDto request) {
        log.info("Received request to add new candidate: {}", request);
        boolean addedCandidate = candidateService.addCandidate(request);
        log.info("Candidate add operation result: {}", addedCandidate ? "SUCCESS" : "FAILURE");

        GenericResponseDto response = addedCandidate
                ? new GenericResponseDto(UserConstants.MESSAGE_201, UserConstants.STATUS_201)
                : new GenericResponseDto(UserConstants.MESSAGE_500, UserConstants.STATUS_500);

        return new ResponseEntity<>(response, addedCandidate ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "Update Candidate", description = "Update existing candidate details")
    @ApiResponse(
            responseCode = "200",
            description = "Candidate updated successfully",
            content = @Content(schema = @Schema(implementation = GenericResponseDto.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    @PutMapping("/{candidateId}")
    public ResponseEntity<GenericResponseDto> updateCandidate(@PathVariable Long candidateId, @Valid @RequestBody CandidateDto request) {
        log.info("Received request to update candidate with ID {}: {}", candidateId, request);
        boolean isUpdated = candidateService.updateCandidate(candidateId, request);
        log.info("Candidate update operation result for ID {}: {}", candidateId, isUpdated ? "SUCCESS" : "FAILURE");

        GenericResponseDto response = isUpdated
                ? new GenericResponseDto(UserConstants.MESSAGE_200, UserConstants.STATUS_200)
                : new GenericResponseDto(UserConstants.MESSAGE_500, UserConstants.STATUS_500);

        return new ResponseEntity<>(response, isUpdated ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "Get Candidate by ID", description = "Fetch candidate details by ID")
    @ApiResponse(
            responseCode = "200",
            description = "Candidate retrieved successfully",
            content = @Content(schema = @Schema(implementation = CandidateResponseDto.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    @GetMapping("/{candidateId}")
    public ResponseEntity<CandidateResponseDto> fetchCandidate(@PathVariable Long candidateId) {
        log.info("Received request to fetch candidate with ID {}", candidateId);
        CandidateResponseDto candidate = candidateService.fetchCandidate(candidateId);
        log.info("Candidate fetched successfully with ID {}", candidateId);
        return ResponseEntity.ok(candidate);
    }

    @Operation(summary = "Get All Candidates", description = "Fetch all candidate details")
    @ApiResponse(
            responseCode = "200",
            description = "All candidates retrieved successfully",
            content = @Content(schema = @Schema(implementation = CandidateResponseDto.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    @GetMapping
    public ResponseEntity<List<CandidateResponseDto>> fetchAllCandidates() {
        log.info("Received request to fetch all candidates");
        List<CandidateResponseDto> candidates = candidateService.fetchAllCandidates();
        log.info("All candidates fetched successfully, count: {}", candidates.size());
        return ResponseEntity.ok(candidates);
    }
}
