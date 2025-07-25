package com.learning.candidatemanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("candidate")
    private CandidateDto candidateDto;

    public UserDto(String username, String password, CandidateDto candidateDto) {
        this.username = username;
        this.password = password;
        this.candidateDto = candidateDto;
    }
    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "Password must be 8–20 characters long, include upper & lower case letters, a number, and a special character"
    )
    private String password;
    private Set<String> roles;
}
