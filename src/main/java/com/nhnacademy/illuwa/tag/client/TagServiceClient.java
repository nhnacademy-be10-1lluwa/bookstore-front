package com.nhnacademy.illuwa.tag.client;

import com.nhnacademy.illuwa.config.FeignClientConfig;
import com.nhnacademy.illuwa.tag.dto.PageResponse;
import com.nhnacademy.illuwa.tag.dto.TagCreateRequest;
import com.nhnacademy.illuwa.tag.dto.TagResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "tagClient", configuration = FeignClientConfig.class)
public interface TagServiceClient {


    @PostMapping("/api/admin/tags")
    TagResponse createTag(@RequestBody TagCreateRequest tagRequest);

    @DeleteMapping("/api/admin/tags/{id}")
    void deleteTag(@PathVariable Long id);

    @GetMapping("/api/admin/tags")
    PageResponse<TagResponse> getAllTags(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort
    );


}
