package com.nhnacademy.illuwa.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestLoginRequest {
    @NotBlank
    private String orderNumber;
    @NotBlank
    private String orderPassword;
}
