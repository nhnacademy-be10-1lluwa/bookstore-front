package com.nhnacademy.illuwa.order.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.illuwa.common.dto.ErrorResponse;
import com.nhnacademy.illuwa.common.exception.ApiRequestException;
import com.nhnacademy.illuwa.order.dto.PackagingRequestDto;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;
import com.nhnacademy.illuwa.order.service.PackagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PackagingServiceImpl implements PackagingService {

    private final RestTemplate restTemplate;
    private final String apiUrl = "http://localhost:10305/api/packaging";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<PackagingResponseDto> getPackagingListFromApi() {
        try {
            ResponseEntity<List<PackagingResponseDto>> response =
                    restTemplate.exchange(
                            apiUrl,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<PackagingResponseDto>>() {});

            return response.getStatusCode().is2xxSuccessful() && response.getBody() != null
                    ? response.getBody()
                    : Collections.emptyList();
        } catch (RestClientException e) {
            throw new ApiRequestException("서버와 통신 중 장애 발생");
        }
    }

    @Override
    public PackagingResponseDto registerPackaging(PackagingRequestDto packagingRequestDto) {

        ResponseEntity<PackagingResponseDto> response;

        try {
            response = restTemplate.postForEntity(apiUrl, packagingRequestDto, PackagingResponseDto.class);

        } catch (RestClientException e) {  // 그 외 RestClientException (네트워크 오류, 5xx 서버 오류)
            if (e instanceof HttpServerErrorException serverError) {
                try {
                    ErrorResponse errorBody = objectMapper.readValue(serverError.getResponseBodyAsString(), ErrorResponse.class);
                    throw new ApiRequestException("백엔드 서버 내부 오류: " + errorBody.getMessage());
                } catch (Exception parseException) {
                    System.err.println("서버 오류 응답 본문 파싱 실패: " + parseException.getMessage());
                    throw new ApiRequestException("서버와 통신 중 예상치 못한 오류가 발생했습니다. (5xx)");
                }
            } else {
                throw new ApiRequestException("서버와 연결할 수 없습니다. 네트워크 상태를 확인해주세요.");
            }
        }
        return response.getBody();

    }
}
