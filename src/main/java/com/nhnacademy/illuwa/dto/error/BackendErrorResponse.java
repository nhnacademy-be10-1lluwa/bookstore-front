package com.nhnacademy.illuwa.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackendErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String code;
    private String message;
}