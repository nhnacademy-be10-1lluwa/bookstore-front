package com.nhnacademy.illuwa.memberaddress.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.memberaddress.dto.MemberAddressRequest;
import com.nhnacademy.illuwa.memberaddress.dto.MemberAddressResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "userClientForAddress")
public interface MemberAddressServiceClient {

    @GetMapping("/api/members/addresses")
    List<MemberAddressResponse> getAddressList();

    @GetMapping("/api/members/addresses/count")
    int getAddressCount();

    @GetMapping("/api/members/addresses/paged")
    PageResponse<MemberAddressResponse> getPagedAddressList(@RequestParam("page") int page, @RequestParam("size") int size);

    @GetMapping("/api/members/addresses/{addressId}")
    MemberAddressResponse getAddress(@PathVariable long addressId);

    @PostMapping("/api/members/addresses")
    MemberAddressResponse createAddress(@RequestBody MemberAddressRequest addressRequest);

    @PostMapping("/api/members/addresses/{addressId}")
    MemberAddressResponse updateAddress(@Valid @RequestBody MemberAddressRequest addressRequest, @PathVariable long addressId);

    @DeleteMapping("/api/members/addresses/{addressId}")
    void deleteAddress(@PathVariable long addressId);

    @PostMapping("/api/members/addresses/set-default/{addressId}")
    void setDefaultAddress(@PathVariable long addressId);

}