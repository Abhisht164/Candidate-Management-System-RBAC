package com.learning.candidatemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenericResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String  status;
    private String message;
}
