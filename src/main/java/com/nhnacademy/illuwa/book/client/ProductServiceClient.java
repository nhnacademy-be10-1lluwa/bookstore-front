package com.nhnacademy.illuwa.book.client;

import com.nhnacademy.illuwa.book.dto.*;
import com.nhnacademy.illuwa.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "bookClient", configuration = FeignClientConfig.class)
public interface ProductServiceClient {
    @GetMapping("/api/books/{id}")
    BookDetailResponse getBookDetail(@PathVariable String id);

    @GetMapping("/api/books/search")
    List<SearchBookResponse> findBooks();

    @GetMapping("/api/books/bestseller")
    List<BestSellerResponse> getBestSeller();

    @GetMapping("/api/books")
    List<BookDetailResponse> getRegisteredBook();

    // 알라딘 API를 통해 도서 검색
    @GetMapping("/api/admin/books/external")
    ResponseEntity<List<Object>> searchAladinBooks(@RequestParam("title") String title);

    // 알라딘 검색을 통해 도서 등록
    @PostMapping("/api/admin/books/register/aladin")
    ResponseEntity<Void> registerBookFromAladin(@RequestBody Object aladinBookCreateRequest);


    //도서 직접 등록
    @PostMapping(value = "/api/admin/books/register/manual", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> registerBookManual(
            @RequestPart("request") BookRegisterRequest bookRegisterRequest,
            @RequestPart("imageFile") MultipartFile imageFile
    );

    @GetMapping("/api/books")
    List<BookDetailResponse> getAllBook();

    @PostMapping("/api/admin/books/register/api")
    void registerBookByAladin(@RequestBody FinalAladinBookRegisterRequest bookRegisterRequest);

    @GetMapping("/api/books/isbn/{isbn}")
    BookDetailResponse findBookByIsbn(@PathVariable String isbn);


    @GetMapping("/categories/tree")
    public List<CategoryResponse> getCategoryTree();


}
