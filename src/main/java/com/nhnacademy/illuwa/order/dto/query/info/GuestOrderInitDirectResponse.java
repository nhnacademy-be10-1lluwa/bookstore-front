package com.nhnacademy.illuwa.order.dto.query.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestOrderInitDirectResponse {
    private BookPriceResponse item;
    private List<PackagingResponse> packaging;
}
