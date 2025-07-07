package com.nhnacademy.illuwa.memberaddress.controller;

import com.nhnacademy.illuwa.memberaddress.client.MemberAddressServiceClient;
import com.nhnacademy.illuwa.memberaddress.dto.MemberAddressRequest;
import com.nhnacademy.illuwa.memberaddress.dto.MemberAddressResponse;
import com.nhnacademy.illuwa.memberaddress.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MemberAddressController {

    private final MemberAddressServiceClient memberAddressServiceClient;

    //주소 등록 폼
    @GetMapping("/addresses/new")
    public String showCreateForm(Model model) {
        model.addAttribute("mode", "new");
        model.addAttribute("address", null);
        return "memberaddress/address_detail";
    }

    //주소 상세 조회 (뷰 모드)
    @GetMapping("/addresses/{addressId}")
    public String showDetail(@PathVariable long addressId, Model model) {
        MemberAddressResponse address = memberAddressServiceClient.getAddress(addressId);
        model.addAttribute("mode", "view");
        model.addAttribute("address", address);
        return "memberaddress/address_detail";
    }

    //주소 수정 폼 (수정 모드)
    @GetMapping("/addresses/edit/{addressId}")
    public String showEditForm(@PathVariable long addressId, Model model) {
        MemberAddressResponse address = memberAddressServiceClient.getAddress(addressId);
        model.addAttribute("mode", "edit");
        model.addAttribute("address", address);
        return "memberaddress/address_detail";
    }

    // 주소 저장 (신규/수정)
    @PostMapping("/addresses/save")
    public String saveAddress(
            @RequestParam("mode") String mode,
            @Valid MemberAddressRequest request,
            BindingResult bindingResult,
            @RequestParam(value = "addressId", required = false) Long addressId,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", mode);
            model.addAttribute("address", request);
            return "memberaddress/address_detail";
        }

        MemberAddressResponse saved = mode.equals("edit")
                ? memberAddressServiceClient.updateAddress(request, addressId)
                : memberAddressServiceClient.createAddress(request);

        String message = addressId != null
                        ? "주소가 성공적으로 수정되었습니다! \uD83C\uDF89"
                        : "주소가 성공적으로 등록되었습니다! \uD83C\uDF89";
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/address-list";
    }

    //주소 목록 조회
    @GetMapping("/address-list")
    public String listAddresses(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        int pageSize = 5;

        PageResponse<MemberAddressResponse> addressPage =
                memberAddressServiceClient.getPagedAddressList(page, pageSize);

        model.addAttribute("addressList", addressPage.getContent());
        model.addAttribute("currentPage", addressPage.getNumber());
        model.addAttribute("totalPages", addressPage.getTotalPages());

        return "memberaddress/address_list";
    }

    // 주소 삭제
    @PostMapping("/addresses/delete/{addressId}")
    public String deleteAddress(@PathVariable long addressId, Model model) {
        memberAddressServiceClient.deleteAddress(addressId);
        model.addAttribute("addressList", memberAddressServiceClient.getAddressList());
        return "redirect:/address-list";
    }

    //기본 주소지로 설정
    @PostMapping("/addresses/set-default/{addressId}")
    public String setDefaultAddress(@PathVariable long addressId){
        memberAddressServiceClient.setDefaultAddress(addressId);
        return "redirect:/address-list";
    }
}
