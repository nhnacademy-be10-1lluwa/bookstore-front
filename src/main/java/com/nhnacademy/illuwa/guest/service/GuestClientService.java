package com.nhnacademy.illuwa.guest.service;

import com.nhnacademy.illuwa.guest.client.GuestServiceClient;
import com.nhnacademy.illuwa.guest.dto.GuestResponse;
import com.nhnacademy.illuwa.order.dto.GuestLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestClientService {
    private final GuestServiceClient guestServiceClient;

    public GuestResponse getGuest(GuestLoginRequest request){
        return guestServiceClient.getGuest(request);
    }
}
