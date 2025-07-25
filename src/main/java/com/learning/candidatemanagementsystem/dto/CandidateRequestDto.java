package com.learning.candidatemanagementsystem.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CandidateRequestDto {

    private Long userId;
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String fullName;
    @Email
    private String email;
    @Lob
    private String resume;

    public CandidateRequestDto( Long id, String fullName, String email, String resume) {
        this.userId = id;
        this.fullName = fullName;
        this.email = email;
        this.resume = resume;
    }
}
