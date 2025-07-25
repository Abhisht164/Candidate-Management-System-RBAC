package com.learning.candidatemanagementsystem.controller;

import com.learning.candidatemanagementsystem.dto.AuthRequestDto;
import com.learning.candidatemanagementsystem.dto.AuthResponseDto;
import com.learning.candidatemanagementsystem.dto.ErrorResponseDto;
import com.learning.candidatemanagementsystem.security.JwtUtil;
import com.learning.candidatemanagementsystem.security.service.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(
        name = "Authentication Handler",
        description = "APIs for user authentication and JWT token generation"
)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "User Login",
            description = "Authenticates a user and returns a JWT token upon successful login"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Login successful",
            content = @Content(schema = @Schema(implementation = AuthResponseDto.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Invalid credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDto authRequest) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            log.info("Token generated for user: {}", authRequest.getUsername()); //
            return ResponseEntity.ok(new AuthResponseDto(token));
        } catch (BadCredentialsException ex) {
            log.warn("Login failed for user [{}] - Invalid credentials", authRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }
}

