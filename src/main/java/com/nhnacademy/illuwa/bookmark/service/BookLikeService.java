package com.nhnacademy.illuwa.bookmark.service;

import com.nhnacademy.illuwa.bookmark.client.BookLikeServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookLikeService {
    private final BookLikeServiceClient bookLikeServiceClient;


}
