package com.nhnacademy.illuwa.address.controller;

import com.nhnacademy.illuwa.address.client.AddressServiceClient;
import com.nhnacademy.illuwa.address.dto.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddressController {
    private final AddressServiceClient addressServiceClient;

    @GetMapping("/address-list")
    public String myAddressList(Model model){
        List<AddressResponse> addressList = addressServiceClient.getAddressList();
        model.addAttribute("addresses", addressList);
        return "address/address_list";
    }

    @GetMapping("/address-detail/{addressId}")
    public String myAddressDetail(@PathVariable long addressId, Model model){
        AddressResponse address = addressServiceClient.getAddress(addressId);
        model.addAttribute("address", address);
        return "address/address_detail";
    }
    // TODO Address 수정, 삭제 구현해야함
}
