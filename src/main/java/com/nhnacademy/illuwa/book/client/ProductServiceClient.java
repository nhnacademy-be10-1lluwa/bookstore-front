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

    // GET 도서ID로 도서검색
    @GetMapping("/api/books/{id}")
    BookDetailResponse getBookDetail(@PathVariable String id);


    //인기 도서 목록 불러오기
    @GetMapping("/api/books/bestseller")
    List<BestSellerResponse> getBestSeller();

    //GET DB에 저장된 도서 목록
    @GetMapping("/api/books")
    List<BookDetailResponse> getRegisteredBook();

    // 알라딘 API를 통해 도서 검색
    @GetMapping("/api/admin/books/external")
    List<BookExternalResponse> searchAladinBooksByTitle(@RequestParam("title") String title);

    //POST 알라딘 API를 활용해 도서 직접 등록
    @PostMapping(value = "/api/admin/books/register/aladin")
    ResponseEntity<Void> registerBookByApi(BookApiRegisterRequest bookApiRegisterRequest);


    //POST 도서 직접 등록
    @PostMapping(value = "/api/admin/books/register/manual", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> registerBookManual(
            @RequestPart("request") BookRegisterRequest bookRegisterRequest,
            @RequestPart("imageFile") MultipartFile imageFile
    );

    //GET 도서 ISBN
    @GetMapping("/api/books/isbn/{isbn}")
    BookDetailResponse findBookByIsbn(@PathVariable String isbn);

    //GET 도서 ISBN
    @GetMapping("/api/books/external/isbn/{isbn}")
    BookExternalResponse findBookByApi(@PathVariable String isbn);


    //GET 카테고리
    @GetMapping("/api/categories/tree")
    public List<CategoryResponse> getCategoryTree();

//    @PostMapping("/api/admin/books/register/api")
//    void registerBookByAladin(@RequestBody FinalAladinBookRegisterRequest bookRegisterRequest);


    // 알라딘 검색을 통해 도서 등록
//    @PostMapping("/api/admin/books/register/aladin")
//    ResponseEntity<Void> registerBookFromAladin(@RequestBody Object aladinBookCreateRequest);


//    GET DB에 저장된 도서 목록
    @GetMapping("/api/books/search")
    List<SearchBookResponse> findBooks();


    // 도서 삭제
    @DeleteMapping("/api/admin/books/{id}")
    void deleteBook(@PathVariable Long id);


    @GetMapping("/api/admin/books/{id}/detail")
    BookDetailWithExtraInfoResponse getBookDetailWithExtraInfo(@PathVariable String id);


    // 도서 수정
    @PostMapping("/api/admin/books/{id}/update")
    void updateBook(@PathVariable("id") Long id, @RequestBody BookUpdateRequest request);


    @GetMapping("/api/admin/books/extra_info")
    List<BookDetailWithExtraInfoResponse> getAllBooksWithExtraInfo();

}
