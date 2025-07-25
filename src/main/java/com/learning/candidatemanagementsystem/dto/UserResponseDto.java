package com.learning.candidatemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String username;
    private String password;
}
