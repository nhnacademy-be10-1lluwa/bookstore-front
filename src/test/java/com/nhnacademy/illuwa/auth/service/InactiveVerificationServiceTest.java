package com.nhnacademy.illuwa.auth.service;

import com.nhnacademy.illuwa.auth.client.InactiveVerificationServiceClient;
import com.nhnacademy.illuwa.auth.dto.message.InactiveCheckResponse;
import com.nhnacademy.illuwa.auth.dto.message.SendVerificationRequest;
import com.nhnacademy.illuwa.member.enums.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InactiveVerificationServiceTest {
    @Mock
    InactiveVerificationServiceClient inactiveVerificationServiceClient;

    @InjectMocks
    InactiveVerificationService inactiveVerificationService;

    @Test
    void getInactiveMemberInfo_shouldDelegateToClient() {
        // given
        SendVerificationRequest request = new SendVerificationRequest("email@test.com");
        InactiveCheckResponse expected = InactiveCheckResponse.builder()
                .status(Status.INACTIVE)
                .name("riveroad")
                .email("email@test.com")
                .build();

        when(inactiveVerificationServiceClient.getInactiveMemberInfo(request)).thenReturn(expected);

        InactiveCheckResponse result = inactiveVerificationService.getInactiveMemberInfo(request);

        assertThat(result).isEqualTo(expected);
        verify(inactiveVerificationServiceClient).getInactiveMemberInfo(request);
    }
}
