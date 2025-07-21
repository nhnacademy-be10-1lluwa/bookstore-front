package com.nhnacademy.illuwa.guest.client;

import com.nhnacademy.illuwa.guest.dto.GuestResponse;
import com.nhnacademy.illuwa.auth.dto.GuestLoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "userClientForGuest")
public interface GuestServiceClient {
    @PostMapping("/api/guests")
    GuestResponse getGuest(@RequestBody GuestLoginRequest request);
}
