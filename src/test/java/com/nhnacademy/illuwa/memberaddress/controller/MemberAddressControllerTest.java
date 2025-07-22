package com.nhnacademy.illuwa.memberaddress.controller;

import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.memberaddress.client.MemberAddressServiceClient;
import com.nhnacademy.illuwa.memberaddress.dto.MemberAddressRequest;
import com.nhnacademy.illuwa.memberaddress.dto.MemberAddressResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberAddressController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class MemberAddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    MemberAddressServiceClient memberAddressServiceClient;

    @Test
    @DisplayName("주소 등록 폼 페이지 접근")
    void testShowCreateForm() throws Exception {
        given(memberAddressServiceClient.getAddressCount()).willReturn(1);

        mockMvc.perform(get("/addresses/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("memberaddress/address_detail"))
                .andExpect(model().attributeExists("addressCount"))
                .andExpect(model().attribute("mode", "new"))
                .andExpect(model().attribute("address", nullValue()));

        verify(memberAddressServiceClient, times(1)).getAddressCount();
        verifyNoMoreInteractions(memberAddressServiceClient);
    }

    @Test
    @DisplayName("주소 상세 조회")
    void testShowDetail() throws Exception {
        MemberAddressResponse address = MemberAddressResponse.builder()
                .memberId(1L)
                .memberAddressId(10L)
                .addressName("우리집")
                .recipientName("홍길동")
                .recipientContact("010-1234-5678")
                .postCode("11111")
                .address("서울 어딘가")
                .detailAddress("101동 202호")
                .defaultAddress(true)
                .forcedDefaultAddress(false)
                .build();

        given(memberAddressServiceClient.getAddress(10L)).willReturn(address);

        mockMvc.perform(get("/addresses/10"))
                .andExpect(status().isOk())
                .andExpect(view().name("memberaddress/address_detail"))
                .andExpect(model().attribute("mode", "view"))
                .andExpect(model().attribute("address", address));

        verify(memberAddressServiceClient, times(1)).getAddress(10L);
        verifyNoMoreInteractions(memberAddressServiceClient);
    }

    @Test
    @DisplayName("주소 수정 폼 접근")
    void testShowEditForm() throws Exception {
        MemberAddressResponse address = MemberAddressResponse.builder()
                .memberId(1L)
                .memberAddressId(11L)
                .addressName("회사")
                .recipientName("김길동")
                .recipientContact("010-2222-2222")
                .postCode("22222")
                .address("부산 어딘가")
                .detailAddress("201동 203호")
                .defaultAddress(false)
                .forcedDefaultAddress(false)
                .build();

        given(memberAddressServiceClient.getAddress(11L)).willReturn(address);

        mockMvc.perform(get("/addresses/11/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("memberaddress/address_detail"))
                .andExpect(model().attribute("mode", "edit"))
                .andExpect(model().attribute("address", address));

        verify(memberAddressServiceClient, times(1)).getAddress(11L);
        verifyNoMoreInteractions(memberAddressServiceClient);
    }

    @Test
    @DisplayName("주소 신규 등록 성공")
    void testSaveAddress_create() throws Exception {
        MemberAddressRequest req = MemberAddressRequest.builder()
                .postCode("12345")
                .address("서울시 강남구 강남대로 1")
                .detailAddress("101동 202호")
                .addressName("우리집")
                .recipientName("홍길동")
                .recipientContact("010-1234-5678")
                .defaultAddress(true)
                .build();

        mockMvc.perform(post("/addresses/save")
                        .param("mode", "new")
                        .param("postCode", req.getPostCode())
                        .param("address", req.getAddress())
                        .param("detailAddress", req.getDetailAddress())
                        .param("addressName", req.getAddressName())
                        .param("recipientName", req.getRecipientName())
                        .param("recipientContact", req.getRecipientContact())
                        .param("defaultAddress", String.valueOf(req.isDefaultAddress())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addresses"));

        verify(memberAddressServiceClient, times(1)).createAddress(any(MemberAddressRequest.class));
        verifyNoMoreInteractions(memberAddressServiceClient);
    }

    @Test
    @DisplayName("주소 수정 성공")
    void testSaveAddress_edit() throws Exception {
        MemberAddressRequest req = MemberAddressRequest.builder()
                .postCode("54321")
                .address("부산시 해운대구 해운대로 1")
                .detailAddress("303동 1002호")
                .addressName("회사")
                .recipientName("김철수")
                .recipientContact("010-8765-4321")
                .defaultAddress(false)
                .build();

        MemberAddressResponse updated = MemberAddressResponse.builder()
                .memberId(2L)
                .memberAddressId(22L)
                .addressName("회사")
                .recipientName("김철수")
                .recipientContact("010-8765-4321")
                .postCode("54321")
                .address("부산시 해운대구 해운대로 1")
                .detailAddress("303동 1002호")
                .defaultAddress(false)
                .forcedDefaultAddress(false)
                .build();

        given(memberAddressServiceClient.updateAddress(any(), eq(22L))).willReturn(updated);

        mockMvc.perform(post("/addresses/save")
                        .param("mode", "edit")
                        .param("addressId", "22")
                        .param("postCode", req.getPostCode())
                        .param("address", req.getAddress())
                        .param("detailAddress", req.getDetailAddress())
                        .param("addressName", req.getAddressName())
                        .param("recipientName", req.getRecipientName())
                        .param("recipientContact", req.getRecipientContact())
                        .param("defaultAddress", String.valueOf(req.isDefaultAddress())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addresses"));

        verify(memberAddressServiceClient, times(1)).updateAddress(any(MemberAddressRequest.class), eq(22L));
        verifyNoMoreInteractions(memberAddressServiceClient);
    }

    @Test
    @DisplayName("주소 목록 화면 정상조회")
    void testListAddresses() throws Exception {
        List<MemberAddressResponse> addressList = List.of(
                MemberAddressResponse.builder().memberId(1L).memberAddressId(1L)
                        .addressName("우리집").recipientName("홍길동").recipientContact("010-1234-5678")
                        .postCode("11111").address("서울 어딘가").detailAddress("101동 202호")
                        .defaultAddress(true).forcedDefaultAddress(false).build(),
                MemberAddressResponse.builder().memberId(2L).memberAddressId(2L)
                        .addressName("회사").recipientName("김길동").recipientContact("010-2222-2222")
                        .postCode("22222").address("부산 어딘가").detailAddress("201동 203호")
                        .defaultAddress(false).forcedDefaultAddress(false).build()
        );

        PageResponse<MemberAddressResponse> page = new PageResponse<>(
                addressList,
                1,    // totalPages
                2L,   // totalElements
                0,    // page
                5,    // size
                true, // last
                true  // first
        );

        given(memberAddressServiceClient.getPagedAddressList(0, 5)).willReturn(page);

        mockMvc.perform(get("/addresses")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("memberaddress/address_list"))
                .andExpect(model().attribute("addressList", addressList))
                .andExpect(model().attribute("addressCount", 2L))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1));

        verify(memberAddressServiceClient, times(1)).getPagedAddressList(0, 5);
        verifyNoMoreInteractions(memberAddressServiceClient);
    }

    @Test
    @DisplayName("주소 삭제 성공")
    void testDeleteAddress() throws Exception {
        // 삭제 후 리스트 (갱신)
        List<MemberAddressResponse> addressList = List.of();

        given(memberAddressServiceClient.getAddressList()).willReturn(addressList);

        mockMvc.perform(post("/addresses/3/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addresses"));

        verify(memberAddressServiceClient, times(1)).deleteAddress(3L);
        verify(memberAddressServiceClient, times(1)).getAddressList();
        verifyNoMoreInteractions(memberAddressServiceClient);
    }

    @Test
    @DisplayName("기본주소지로 설정 성공")
    void testSetDefaultAddress() throws Exception {
        mockMvc.perform(post("/addresses/4/default"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addresses"));

        verify(memberAddressServiceClient, times(1)).setDefaultAddress(4L);
        verifyNoMoreInteractions(memberAddressServiceClient);
    }
}
