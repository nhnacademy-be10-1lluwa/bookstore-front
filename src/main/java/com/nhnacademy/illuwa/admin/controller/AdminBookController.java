package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.book.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/book")
public class AdminBookController {

    private final ProductServiceClient productServiceClient;

    @GetMapping("/books")
    public String adminBookPage() {
        return "admin/book/books";
    }

    @GetMapping("/manage")
    public String bookManagePage(Model model) {
        List<BookDetailWithExtraInfoResponse> registeredBooks = productServiceClient.getAllBooksWithExtraInfo();
        model.addAttribute("books", registeredBooks);
        return "admin/book/manage";
    }



    @GetMapping("/search_api")
    public String searchApiAndShowResults(@RequestParam("title") String title, Model model) {
        List<BookExternalResponse> searchResults;

        searchResults = productServiceClient.searchAladinBooksByTitle(title);

        model.addAttribute("searchResults", searchResults);
        return "admin/book/book_search_results";
    }


    // DB에 등록된 도서를 GET By ISBN -> 도서 등록 form(api)
    @GetMapping("/register_from_api/{isbn}")
    public String showRegisterFormFromApi(@PathVariable String isbn, Model model) {
        BookExternalResponse response = productServiceClient.findBookByApi(isbn);

        List<CategoryResponse> categoryTree = productServiceClient.getCategoryTree();

        model.addAttribute("book", response);
        model.addAttribute("categoryTree", categoryTree);

        return "admin/book/book_register_api";
    }

    // 도서 등록 form(api) -> 도서 등록 -> 도서 관리 페이지 이동
    @PostMapping("/register_api")
    public String registerBookFromApi(BookApiRegisterRequest request) {

        productServiceClient.registerBookByApi(request);
        // 등록 완료 후 도서 관리 목록 페이지 등으로 리다이렉트
        return "redirect:/admin/book/manage"; // 성공 시 이동할 페이지
    }





    //도서 ISBN -> 도서 상세페이지
    @GetMapping("/detail/{isbn}")
    public String bookDetailPage(@PathVariable String isbn) {
        return "admin/book/detail";
    }



    // -> 도서 등록 페이지 이동
    @GetMapping("/book_register")
    public String registerBookPage(Model model){
        List<CategoryResponse> categoryTree = productServiceClient.getCategoryTree();
        model.addAttribute("categoryTree", categoryTree);
        return "admin/book/book_register";
    }



    //도서 등록 form -> 도서 직접 등록
    @PostMapping("/register/manual")
    public String registerBookManual(
            @ModelAttribute BookRegisterRequest bookRegisterRequest) {

            productServiceClient.registerBookManual(bookRegisterRequest, bookRegisterRequest.getImageFile());
            return "redirect:/admin/book/manage";
    }

    // -> 도서 삭제
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id){
        productServiceClient.deleteBook(id);
        return "redirect:/admin/book/manage";
    }

    // -> 도서 수정 페이지
    @GetMapping("/edit/{id}")
    public String updateBookForm(@PathVariable String id, Model model) {
        BookDetailWithExtraInfoResponse book = productServiceClient.getBookDetailWithExtraInfo(id);
        List<CategoryResponse> categoryTree = productServiceClient.getCategoryTree();

        model.addAttribute("book", book);
        model.addAttribute("categoryTree", categoryTree);

        return "admin/book/book_update";
    }

    @PostMapping("/update/{id}")
    public String updateBook(
            @PathVariable Long id,
            @ModelAttribute BookUpdateRequest request
    ) {
        // 여기서 FeignClient 호출은 JSON으로 보내야 하므로
        productServiceClient.updateBook(id, request);

        return "redirect:/admin/book/manage";
    }

}

