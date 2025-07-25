package com.learning.candidatemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private  String apiPath;

    private int errorCode;

    private  String errorMessage;

    private LocalDateTime errorTime;
}
