package com.learning.candidatemanagementsystem.controller;

import com.learning.candidatemanagementsystem.constant.UserConstants;
import com.learning.candidatemanagementsystem.dto.ErrorResponseDto;
import com.learning.candidatemanagementsystem.dto.GenericResponseDto;
import com.learning.candidatemanagementsystem.dto.UserDto;
import com.learning.candidatemanagementsystem.dto.UserResponseDto;
import com.learning.candidatemanagementsystem.service.IAdminUserService;
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
        name = "Admin-User Handler ",
        description = "REST APIs to CREATE, UPDATE and FETCH User details"
)
@Slf4j
@RestController
@RequestMapping(value = "/api/admin/users", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AdminUserController {
    private final IAdminUserService userService;

    @Operation(
            summary = "Create User",
            description = "Create a new User."
    )
    @ApiResponse(
            responseCode = "201",
            description = "User created successfully",
            content = @Content(schema = @Schema(implementation = GenericResponseDto.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    @PostMapping
    public ResponseEntity<GenericResponseDto> createUser(@Valid @RequestBody UserDto request) {
        log.info("Received request to create user with username: {}", request.getUsername());
        boolean isCreated = userService.createUser(request);
        GenericResponseDto response = isCreated
                ? new GenericResponseDto(UserConstants.MESSAGE_201, UserConstants.STATUS_201)
                : new GenericResponseDto(UserConstants.MESSAGE_500, UserConstants.STATUS_500);
        log.info("User creation status for username '{}': {}", request.getUsername(), isCreated ? "SUCCESS" : "FAILED");
        return new ResponseEntity<>(response, isCreated ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "Update User",
            description = "Updates an existing User."
    )
    @ApiResponse(
            responseCode = "200",
            description = "User updated successfully",
            content = @Content(schema = @Schema(implementation = GenericResponseDto.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    @PutMapping("/{userId}")
    public ResponseEntity<GenericResponseDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto request) {
        log.info("Received request to update user with ID: {}", userId);
        boolean isUpdated = userService.updateUser(userId, request);

        GenericResponseDto response = isUpdated
                ? new GenericResponseDto(UserConstants.MESSAGE_200, UserConstants.STATUS_200)
                : new GenericResponseDto(UserConstants.MESSAGE_500, UserConstants.STATUS_500);
        log.info("User update status for ID '{}': {}", userId, isUpdated ? "SUCCESS" : "FAILED");
        return new ResponseEntity<>(response, isUpdated ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "List All User",
            description = "Retrieves all Users."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Users retrieved successfully",
            content = @Content(schema = @Schema(implementation = UserResponseDto.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> fetchAllUsers() {
        log.info("Fetching all users");
        List<UserResponseDto> users = userService.fetchAllUsers();
        log.info("Total users fetched: {}", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
