package com.nhnacademy.illuwa.address.client;

import com.nhnacademy.illuwa.user.dto.AddressRequest;
import com.nhnacademy.illuwa.user.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service", url = "${api.base-url}")
public interface AddressServiceClient {
    @GetMapping("/members/addresses")
    List<AddressResponse> getAddressList();

    @GetMapping("/members/addresses/{addressId}")
    AddressResponse getAddress(@PathVariable long addressId);

    @PostMapping("/members/addresses")
    AddressResponse createAddress(@RequestBody AddressRequest addressRequest);

    @PatchMapping("/members/addresses/{addressId}")
    AddressResponse updateAddress(@RequestBody AddressRequest addressRequest, @PathVariable long addressId);

    @DeleteMapping("/members/addresses/{addressId}")
    void deleteAddress(@PathVariable long addressId);
}
