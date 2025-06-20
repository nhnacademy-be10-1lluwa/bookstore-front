package com.nhnacademy.illuwa.book.service;

import com.nhnacademy.illuwa.book.dto.FindBookResponse;
import com.nhnacademy.illuwa.book.dto.SearchBookResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    List<SearchBookResponse> fetchBookListFromApi();
    public ResponseEntity<FindBookResponse> searchBookListFromApi(String keyword);
}