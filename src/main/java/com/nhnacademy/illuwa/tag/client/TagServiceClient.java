package com.nhnacademy.illuwa.tag.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.config.FeignClientConfig;
import com.nhnacademy.illuwa.tag.dto.TagCreateRequest;
import com.nhnacademy.illuwa.tag.dto.TagResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "tagClient", configuration = FeignClientConfig.class)
public interface TagServiceClient {


    @PostMapping("/api/admin/tags")
    TagResponse createTag(@RequestBody TagCreateRequest tagRequest);

    @DeleteMapping("/api/admin/tags/{id}")
    void deleteTag(@PathVariable Long id);

    @GetMapping("/api/admin/tags")
    PageResponse<TagResponse> getAllTags(Pageable pageable);

    @GetMapping("/api/tags")
    List<TagResponse> getAllTags();

    @GetMapping("/api/admin/books/{bookId}/tags")
    List<TagResponse> getTagsByBookId(@PathVariable Long bookId);

}
