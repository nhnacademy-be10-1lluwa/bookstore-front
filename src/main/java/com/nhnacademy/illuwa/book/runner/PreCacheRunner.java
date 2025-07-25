package com.nhnacademy.illuwa.book.runner;

import com.nhnacademy.illuwa.book.service.BookService;
import com.nhnacademy.illuwa.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreCacheRunner implements ApplicationRunner {
    private final BookService bookService;
    private final CategoryService categoryService;

    @Override
    public void run(ApplicationArguments args) {
        bookService.getBestSellers();
        categoryService.getAllCategories();
    }
}
