package com.learning.candidatemanagementsystem.controller;

import com.learning.candidatemanagementsystem.constant.UserConstants;
import com.learning.candidatemanagementsystem.dto.CandidateDto;
import com.learning.candidatemanagementsystem.dto.CandidateResponseDto;
import com.learning.candidatemanagementsystem.dto.GenericResponseDto;
import com.learning.candidatemanagementsystem.service.ICandidateProfileService;
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

@Tag(
        name = "Candidate Profile Handler",
        description = "APIs for viewing and updating the candidate's profile"
)
@RestController
@RequestMapping(value = "/api/candidate/profile", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Slf4j
public class CandidateProfileController {

    private final ICandidateProfileService candidateService;

    @Operation(summary = "View Candidate Profile", description = "Returns the candidate's profile data")
    @ApiResponse(
            responseCode = "200",
            description = "Profile fetched successfully",
            content = @Content(schema = @Schema(implementation = CandidateResponseDto.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = com.learning.candidatemanagementsystem.dto.ErrorResponseDto.class))
    )
    @GetMapping
    public ResponseEntity<CandidateResponseDto> viewProfile() {
        log.info("Received request to view candidate profile");
        CandidateResponseDto candidateResponseDto = candidateService.viewProfile();
        log.info("Candidate profile fetched successfully");
        return ResponseEntity.ok(candidateResponseDto);
    }

    @Operation(summary = "Update Candidate Profile", description = "Updates candidate's profile data")
    @ApiResponse(
            responseCode = "200",
            description = "Profile updated successfully",
            content = @Content(schema = @Schema(implementation = GenericResponseDto.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = com.learning.candidatemanagementsystem.dto.ErrorResponseDto.class))
    )
    @PutMapping
    public ResponseEntity<GenericResponseDto> updateProfile(@Valid @RequestBody CandidateDto request) {
        log.info("Received request to update candidate profile");
        boolean isUpdated = candidateService.updateProfile(request);
        log.info("Candidate profile update status: {}", isUpdated ? "SUCCESS" : "FAILURE");

        GenericResponseDto response = isUpdated
                ? new GenericResponseDto(UserConstants.MESSAGE_201, UserConstants.STATUS_201)
                : new GenericResponseDto(UserConstants.MESSAGE_500, UserConstants.STATUS_500);

        return new ResponseEntity<>(response, isUpdated ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
