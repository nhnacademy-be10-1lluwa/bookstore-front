package com.nhnacademy.illuwa.search.controller;

import com.nhnacademy.illuwa.search.dto.BookDocument;
import com.nhnacademy.illuwa.search.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/books/search-es")
public class BookSearchController {
    private final BookSearchService bookSearchService;

    @GetMapping()
    public String searchBooks(@RequestParam(required = false) String keyword, @RequestParam(required = false) String category, Pageable pageable, Model model)
    {
        Page<BookDocument> results = null;

        if (category != null && !category.isBlank()) {
            results = bookSearchService.searchBooksByCategory(category, pageable);
        }
        else if (keyword != null && !keyword.isBlank()) {
            results = bookSearchService.searchBooks(keyword, pageable);
        }
        model.addAttribute("books", results.getContent());
        model.addAttribute("page", results);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);

        return "book/search_result";
    }


    @GetMapping("/category")
    public String searchBooksByCategory(@RequestParam String category, Pageable pageable, Model model) {
        Page<BookDocument> results = bookSearchService.searchBooksByCategory(category, pageable);
        model.addAttribute("books", results.getContent());
        model.addAttribute("page", results);
        model.addAttribute("category", category);
        return "book/search_result";
    }
}