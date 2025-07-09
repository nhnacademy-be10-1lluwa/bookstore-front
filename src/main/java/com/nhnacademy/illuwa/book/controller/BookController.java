package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.dto.BookExternalResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.print.Book;

@Controller
public class BookController {

    ProductServiceClient productServiceClient;

    public BookController(ProductServiceClient productServiceClient){
        this.productServiceClient = productServiceClient;
    }

    @GetMapping("/user/book_info/{isbn}")
    public String bookInfo(@PathVariable String isbn, Model model) {
        return "book/book_info";
    }


    @GetMapping("/user/books/{isbn}")
    public String bookDetail(@PathVariable("isbn") String isbn, Model model) {
        BookDetailResponse bookDetail = productServiceClient.findBookByIsbn(isbn);
        model.addAttribute("book", bookDetail);
        return "book/detail";
    }

}
