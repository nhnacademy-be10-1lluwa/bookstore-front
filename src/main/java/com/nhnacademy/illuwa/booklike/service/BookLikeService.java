package com.nhnacademy.illuwa.booklike.service;

import com.nhnacademy.illuwa.booklike.client.BookLikeServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookLikeService {
    private final BookLikeServiceClient bookLikeServiceClient;


}
