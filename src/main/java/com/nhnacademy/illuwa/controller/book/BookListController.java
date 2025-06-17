package com.nhnacademy.illuwa.controller.book;

import com.nhnacademy.illuwa.dto.book.FindBookResponse;
import com.nhnacademy.illuwa.dto.book.SearchBookResponse;
import com.nhnacademy.illuwa.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookListController {
    private final BookService bookService;

    @GetMapping("/book_list")
    public String bookList(Model model) {

        // 테스트용 리스트 추가
        List<SearchBookResponse> books = new ArrayList<>();
        books.add(new SearchBookResponse("테스트 도서", "테스트 내용", "테스트 설명", "카테고리", "홍길동", "테스트출판사", "2025-01-01",
                "9788999999999", new BigDecimal(10000), new BigDecimal(9999), true, "/images/test.jpg"));

        model.addAttribute("books", books);

//        List<SearchBookResponse> books = bookService.fetchBookListFromApi();
//        model.addAttribute("books", books);
        return "book/book_list";
    }

    @PostMapping("/book_search")
    public String searchBook(@RequestParam("keyword") String keyword, Model model) {

        FindBookResponse book = bookService.searchBookListFromApi(keyword).getBody();
        List<FindBookResponse> books = new ArrayList<>();
        books.add(book);

        model.addAttribute("books", books);
        return "book/book_list";
    }
}
