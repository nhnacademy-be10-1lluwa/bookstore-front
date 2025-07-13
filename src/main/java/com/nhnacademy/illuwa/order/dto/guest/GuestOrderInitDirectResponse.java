package com.nhnacademy.illuwa.order.dto.guest;

import com.nhnacademy.illuwa.order.dto.BookItemOrderDto;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestOrderInitDirectResponse {
    private BookItemOrderDto item;
    private List<PackagingResponseDto> packaging;
}
