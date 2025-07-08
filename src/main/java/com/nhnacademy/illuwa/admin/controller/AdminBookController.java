package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.book.dto.BookRegisterRequest;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


// front-service의 페이지로 이동은 Page 네이밍

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/book")
public class AdminBookController {

    private final ProductServiceClient productServiceClient;

    @GetMapping("/register")
    public String bookRegisterPage() {
        return "admin/book/book_register";
    }

    @GetMapping("/search/aladin")
    @ResponseBody
    public ResponseEntity<List<Object>> searchAladinBooks(@RequestParam("title") String title) {
        return productServiceClient.searchAladinBooks(title);
    }


    @PostMapping("/register/aladin")
    public ResponseEntity<Void> registerBookFromAladin(@RequestBody Object aladinBookCreateRequest) {
        return productServiceClient.registerBookFromAladin(aladinBookCreateRequest);
    }

    @GetMapping("/list")
    public String bookListPage() {
        return "admin/book/book";
    }

    @GetMapping("/books")
    public String adminBookPage() {
        return "admin/book/books";
    }

    @GetMapping("/detail/{isbn}")
    public String bookDetailPage(@PathVariable String isbn) {
        return "admin/book/book_detail";
    }

    @GetMapping("/book_register")
    public String registerBookPage(){
        return "admin/book/book_register";
    }

    @PostMapping("/register/manual")
    public String registerBookManual(
            @ModelAttribute("BookRegisterRequest") BookRegisterRequest bookRegisterRequest) {


        log.info("도서 등록 요청: title={}, imageFile size={}",
                bookRegisterRequest.getTitle());

        try {
            log.info("Front-service에서 전송: title={}, file size={}",
                    bookRegisterRequest.getTitle(), bookRegisterRequest.getImageFile());
            productServiceClient.registerBookManual(bookRegisterRequest, bookRegisterRequest.getImageFile());
            return "redirect:/admin/book/list";

        } catch (FeignException e) {
            log.error("Feign 에러 발생: status={}, body={}",
                    e.status(), e.contentUTF8());
            throw e;
        }
    }


}
