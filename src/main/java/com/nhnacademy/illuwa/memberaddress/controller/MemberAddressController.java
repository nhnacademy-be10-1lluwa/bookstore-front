package com.nhnacademy.illuwa.memberaddress.controller;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.memberaddress.client.MemberAddressServiceClient;
import com.nhnacademy.illuwa.memberaddress.dto.MemberAddressRequest;
import com.nhnacademy.illuwa.memberaddress.dto.MemberAddressResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class MemberAddressController {

    private final MemberAddressServiceClient memberAddressServiceClient;

    //주소 등록 폼
    @GetMapping("/form")
    public String showCreateForm(Model model) {
        int addressCount = memberAddressServiceClient.getAddressCount();
        model.addAttribute("addressCount", addressCount);
        model.addAttribute("mode", "new");
        model.addAttribute("address", null);
        return "memberaddress/address_detail";
    }

    //주소 상세 조회 (뷰 모드)
    @GetMapping("/{addressId}")
    public String showDetail(@PathVariable long addressId, Model model) {
        MemberAddressResponse address = memberAddressServiceClient.getAddress(addressId);
        model.addAttribute("mode", "view");
        model.addAttribute("address", address);
        return "memberaddress/address_detail";
    }

    //주소 수정 폼 (수정 모드)
    @GetMapping("/{addressId}/form")
    public String showEditForm(@PathVariable long addressId, Model model) {
        MemberAddressResponse address = memberAddressServiceClient.getAddress(addressId);
        model.addAttribute("mode", "edit");
        model.addAttribute("address", address);
        return "memberaddress/address_detail";
    }

    // 주소 저장 (신규/수정)
    @PostMapping("/save")
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

        String message;
        if ("edit".equals(mode)) {
            MemberAddressResponse updated = memberAddressServiceClient.updateAddress(request, addressId);
            if(updated.isForcedDefaultAddress()){
                message = "유일한 주소이므로 기본 배송지로 자동 설정되어 수정되었습니다!";
            }else{
                message = "주소가 성공적으로 수정되었습니다! \uD83C\uDF89";
            }
        } else {
            memberAddressServiceClient.createAddress(request);
            message = "주소가 성공적으로 등록되었습니다! \uD83C\uDF89";
        }

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/addresses";
    }

    //주소 목록 조회
    @GetMapping
    public String listAddresses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        PageResponse<MemberAddressResponse> addressPage =
                memberAddressServiceClient.getPagedAddressList(page, size);

        model.addAttribute("addressList", addressPage.content());
        model.addAttribute("addressCount", addressPage.totalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", addressPage.size());
        model.addAttribute("totalPages", addressPage.totalPages());
        model.addAttribute("lastPageIndex", Math.max(0, addressPage.totalPages() - 1));
        model.addAttribute("activeMenu", "address-list");

        return "memberaddress/address_list";
    }

    // 주소 삭제
    @PostMapping("/{addressId}/delete")
    public String deleteAddress(@PathVariable long addressId, Model model,
                                RedirectAttributes redirectAttributes) {
        memberAddressServiceClient.deleteAddress(addressId);
        model.addAttribute("addressList", memberAddressServiceClient.getAddressList());
        redirectAttributes.addFlashAttribute("message", "주소가 성공적으로 삭제되었습니다! ");
        return "redirect:/addresses";
    }

    //기본 주소지로 설정
    @PostMapping("/{addressId}/default")
    public String setDefaultAddress(@PathVariable long addressId,
                                    RedirectAttributes redirectAttributes){
        memberAddressServiceClient.setDefaultAddress(addressId);
        redirectAttributes.addFlashAttribute("message", "기본주소지로 설정되었습니다! ");
        return "redirect:/addresses";
    }
}