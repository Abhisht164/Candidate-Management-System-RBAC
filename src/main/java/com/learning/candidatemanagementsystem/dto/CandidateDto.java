package com.learning.candidatemanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class CandidateDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private Long userId;
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String fullName;
    @Email
    private String email;
    @Lob
    private String resume;

    public CandidateDto( Long id, String fullName, String email, String resume) {
        this.userId = id;
        this.fullName = fullName;
        this.email = email;
        this.resume = resume;
    }
}
