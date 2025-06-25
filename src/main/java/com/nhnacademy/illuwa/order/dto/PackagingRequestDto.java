package com.nhnacademy.illuwa.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackagingRequestDto {

    @NotBlank
    @Size(max = 20)
    private String packagingName;

    @NotNull
    @Positive
    private BigDecimal packagingPrice;

}
