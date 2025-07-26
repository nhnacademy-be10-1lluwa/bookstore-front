package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.book.dto.*;
import com.nhnacademy.illuwa.book.service.MinioStorageService;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import com.nhnacademy.illuwa.common.aop.annotation.PerformanceLog;
import com.nhnacademy.illuwa.tag.client.TagServiceClient;
import com.nhnacademy.illuwa.tag.dto.TagResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/books")
public class AdminBookController {

    private final ProductServiceClient productServiceClient;
    private final MinioStorageService minioStorageService;
    private final TagServiceClient tagServiceClient;

    // 도서 관리 페이지
    @GetMapping("/management")
    public String adminBookPage() {
        return "admin/book/books";
    }

    // 도서 관리 리스트
    @PerformanceLog
    @GetMapping
    public String bookManagePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort,
            Model model
    ) {
        Page<BookDetailWithExtraInfoResponse> bookPage = productServiceClient.getAllBooksWithExtraInfo(page, size, sort);

        List<BookDetailWithExtraInfoResponse> convertedList = bookPage.getContent().stream()
                .map(book -> {
                    book.setImgUrl(convertMinioUrl(book.getImgUrl()));
                    book.setBookTags(tagServiceClient.getTagsByBookId(book.getId()));
                    return book;
                })
                .toList();

        Page<BookDetailWithExtraInfoResponse> convertedPage = new PageImpl<>(
                convertedList,
                bookPage.getPageable(),
                bookPage.getTotalElements()
        );

        List<TagResponse> allTags = tagServiceClient.getAllTags();

        model.addAttribute("allTags", allTags);
        model.addAttribute("bookPage", convertedPage);
        model.addAttribute("currentSort", sort);
        return "admin/book/manage";
    }

    // 외부 API 도서 검색
    @GetMapping("/search")
    public String searchApiAndShowResults(@RequestParam("title") String title, Model model) {
        List<BookExternalResponse> searchResults;

        searchResults = productServiceClient.searchAladinBooksByTitle(title);

        model.addAttribute("searchResults", searchResults);
        return "admin/book/book_search_results";
    }


    // 외부 도서 등록 폼 : DB에 등록된 도서를 GET By ISBN -> 도서 등록 form(api)
    @GetMapping("/source/{isbn}/form")
    public String showRegisterFormFromApi(@PathVariable String isbn, Model model) {
        BookExternalResponse response = productServiceClient.findBookByApi(isbn);

        List<CategoryResponse> categoryTree = productServiceClient.getCategoryTree("tree");

        model.addAttribute("book", response);
        model.addAttribute("categoryTree", categoryTree);

        return "admin/book/book_register_api";
    }

    // 도서 등록 form(api) -> 도서 등록 -> 도서 관리 페이지 이동
    @PostMapping("/source")
    public String registerBookFromApi(BookApiRegisterRequest request, @RequestParam(value = "coverFile", required = false) MultipartFile coverFile) {

        if (coverFile != null && !coverFile.isEmpty()) {
            String uploadedUrl = minioStorageService.uploadBookImage(coverFile);
            request.setCover(uploadedUrl);
        }


        productServiceClient.registerBookByApi(request);
        // 등록 완료 후 도서 관리 목록 페이지 등으로 리다이렉트
        return "redirect:/admin/books"; // 성공 시 이동할 페이지
    }

    // -> 도서 등록 페이지 이동
    @GetMapping("/form")
    public String registerBookPage(Model model){
        List<CategoryResponse> categoryTree = productServiceClient.getCategoryTree("tree");
        model.addAttribute("categoryTree", categoryTree);
        return "admin/book/book_register";
    }

    // (POST) 도서 등록
    @PostMapping
    public String registerBookManual(
            @ModelAttribute BookRegisterRequest bookRegisterRequest) {

            productServiceClient.registerBookManual(bookRegisterRequest, bookRegisterRequest.getImageFile());
            return "redirect:/admin/books";
    }

    // (POST) 도서 삭제
    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id){
        productServiceClient.deleteBook(id);
        return "redirect:/admin/books";
    }

    // -> 도서 수정 페이지
    @GetMapping("/{id}/form")
    public String updateBookForm(@PathVariable Long id, Model model) {
        BookDetailWithExtraInfoResponse book = productServiceClient.getBookDetailWithExtraInfo(id);
        List<CategoryResponse> categoryTree = productServiceClient.getCategoryTree("tree");

        model.addAttribute("book", book);
        model.addAttribute("categoryTree", categoryTree);

        return "admin/book/book_update";
    }

    // -> (POST) 도서 수정
    @PostMapping("/{id}/update")
    public String updateBook(
            @PathVariable Long id,
            @ModelAttribute BookUpdateRequest request,
            @RequestParam(value = "coverFile", required = false) MultipartFile coverFile) {

        if (coverFile != null && !coverFile.isEmpty()) {
            String newUrl = minioStorageService.uploadBookImage(coverFile);
            request.setCover(newUrl);
        } else {
            request.setCover(request.getCover());
        }

        productServiceClient.updateBook(id, request);

        return "redirect:/admin/books";
    }

    // 도서 이미지 업로드
    @PostMapping("/image")
    @ResponseBody
    public String uploadBookImage(@RequestParam("file") MultipartFile file) {
        String url = minioStorageService.uploadBookImage(file);
        return url;
    }

    private String convertMinioUrl(String originalUrl) {
        if (originalUrl == null) {
            return null;
        }
        return originalUrl.replace(
                "http://storage.java21.net:8000/",
                "https://book1lluwa.store/minio/"
        );
    }

    //태그 등록
    @PostMapping("/{bookId}/tags")
    public String addTagToBook(@PathVariable Long bookId, @RequestParam Long tagId) {
        productServiceClient.addTagToBook(bookId, tagId);
        return "redirect:/admin/books";
    }

    @PostMapping("/{bookId}/tags/{tagId}/delete")
    public String removeTagFromBook(@PathVariable Long bookId, @PathVariable Long tagId) {
        productServiceClient.removeTagFromBook(bookId, tagId);
        return "redirect:/admin/books";
    }
}