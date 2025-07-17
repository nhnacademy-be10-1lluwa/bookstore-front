package com.nhnacademy.illuwa.bookmark.client;

import com.nhnacademy.illuwa.bookmark.dto.BookmarkResponse;
import com.nhnacademy.illuwa.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "ProductClientForBookMark", configuration = FeignClientConfig.class)
public interface BookMarkServiceClient {

    @PostMapping("/api/bookmarks")
    BookmarkResponse toggleBookmark(@RequestParam long bookId);
}
