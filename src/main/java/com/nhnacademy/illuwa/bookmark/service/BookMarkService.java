package com.nhnacademy.illuwa.bookmark.service;

import com.nhnacademy.illuwa.bookmark.client.BookMarkServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMarkService {
    private final BookMarkServiceClient bookMarkServiceClient;


}
