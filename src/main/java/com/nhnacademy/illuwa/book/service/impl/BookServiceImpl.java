package com.nhnacademy.illuwa.book.service.impl;

import com.nhnacademy.illuwa.book.dto.FindBookResponse;
import com.nhnacademy.illuwa.book.dto.SearchBookResponse;
import com.nhnacademy.illuwa.common.exception.ApiRequestException;
import com.nhnacademy.illuwa.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final RestTemplate restTemplate;
    private final String apiUrl = "http://api서버주소/api/books";

    @Override
    public List<SearchBookResponse> fetchBookListFromApi() {
        try{
            ResponseEntity<SearchBookResponse[]> response =
                    restTemplate.getForEntity(apiUrl, SearchBookResponse[].class);

            return response.getStatusCode().is2xxSuccessful() && response.getBody() != null
                    ? Arrays.asList(response.getBody())
                    : Collections.emptyList();
        } catch (RestClientException e) {
            throw new ApiRequestException("서버와 통신 중 장애가 발생했습니다.");
        }
    }

    public ResponseEntity<FindBookResponse> searchBookListFromApi(@RequestParam String keyword) {
        String apiUrl = "http://api서버주소/api/books/search?keyword=" + keyword;

        try {
            ResponseEntity<FindBookResponse> response =
                    restTemplate.getForEntity(apiUrl, FindBookResponse.class);

            return response;
        } catch (RestClientException e) {
            throw new ApiRequestException("서버와 통신 중 장애가 발생했습니다.");
        }
    }
}
