package com.nhnacademy.illuwa.service.book;

import com.nhnacademy.illuwa.dto.book.FindBookResponse;
import com.nhnacademy.illuwa.dto.book.SearchBookResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BookService {
    List<SearchBookResponse> fetchBookListFromApi();
    public ResponseEntity<FindBookResponse> searchBookListFromApi(String keyword);
}