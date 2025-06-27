package com.nhnacademy.illuwa.auth.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaycoCodeRequest {
    private String code;
    private String state;
}

