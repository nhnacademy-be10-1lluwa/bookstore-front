package com.nhnacademy.illuwa.book.client;

import com.nhnacademy.illuwa.book.dto.*;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import com.nhnacademy.illuwa.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import java.util.List;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "bookClient", configuration = FeignClientConfig.class)
public interface ProductServiceClient {

    @GetMapping("/api/books/{id}")
    BookDetailResponse findBookById(@PathVariable Long id);

    // 도서 검색 - ISBN
    @GetMapping("/api/books/isbn/{isbn}")
    BookDetailResponse findBookByIsbn(@PathVariable String isbn);

    // 도서 검색(외부 API) - ISBN
    @GetMapping("/api/external-books/isbn/{isbn}")
    BookExternalResponse findBookByApi(@PathVariable String isbn);

    // 인기 도서 목록
    @GetMapping("/api/books?type=bestseller")
    List<BestSellerResponse> getBestSeller();

    // 도서 목록
    @GetMapping("/api/books")
    List<BookDetailResponse> getRegisteredBook();

    //페이징 처리 도서목록
    @GetMapping("/api/books")
    Page<BookDetailResponse> getRegisteredBookPaged(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(defaultValue = "id") String sort
    );

    // 도서 검색(외부 API)
    @GetMapping("/api/admin/books/external")
    List<BookExternalResponse> searchAladinBooksByTitle(@RequestParam("title") String title);

    //도서 등록(외부 API)
    @PostMapping(value = "/api/admin/books/external")
    ResponseEntity<Void> registerBookByApi(BookApiRegisterRequest bookApiRegisterRequest);


    // 도서 직접 등록
    @PostMapping(value = "/api/admin/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> registerBookManual(
            @RequestPart("request") BookRegisterRequest bookRegisterRequest,
            @RequestPart("imageFile") MultipartFile imageFile
    );

    // 카테고리 목록
    @GetMapping("/api/categories/tree")
    List<CategoryResponse> getCategoryTree();

    // 쿠폰 쪽 카테고리 목록
    @GetMapping("/api/categories")
    List<CategoryResponse> getAllCategories();

    // 도서 삭제
    @DeleteMapping("/api/admin/books/{id}")
    void deleteBook(@PathVariable Long id);

    // 도서 수정
    @PatchMapping("/api/admin/books/{id}")
    void updateBook(@PathVariable("id") Long id, @RequestBody BookUpdateRequest request);

    // 도서 부가정보 포함된 도서 정보
    @GetMapping("/api/admin/books/{id}/detail")
    BookDetailWithExtraInfoResponse getBookDetailWithExtraInfo(@PathVariable Long id);



    // 도서 부가정보 포함된 도서 목록
    @GetMapping("/api/admin/books/details")
    Page<BookDetailWithExtraInfoResponse> getAllBooksWithExtraInfo(@RequestParam int page,
                                                                   @RequestParam int size,
                                                                   @RequestParam(defaultValue = "id") String sort);

    // 도서에 태그 등록
    @PostMapping("/api/admin/books/{bookId}/tags/{tagId}")
    void addTagToBook(@PathVariable("bookId") Long bookId, @PathVariable("tagId") Long tagId);

    // 도서에서 태그 삭제
    @DeleteMapping("/api/admin/books/{bookId}/tags/{tagId}")
    void removeTagFromBook(@PathVariable("bookId") Long bookId, @PathVariable("tagId") Long tagId);


}
