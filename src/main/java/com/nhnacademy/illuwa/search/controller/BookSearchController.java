package com.nhnacademy.illuwa.search.controller;

import com.nhnacademy.illuwa.search.dto.BookDocument;
import com.nhnacademy.illuwa.search.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookSearchController {
    private final BookSearchService bookSearchService;

    @GetMapping("/search")
    public String searchBooks(
            @RequestParam String keyword, Pageable pageable, Model model)
    {


        Page<BookDocument> results = bookSearchService.searchBooks(keyword, pageable);
        model.addAttribute("books", results.getContent());
        model.addAttribute("page", results);
        model.addAttribute("keyword", keyword);
        return "book/search_result";
    }
}