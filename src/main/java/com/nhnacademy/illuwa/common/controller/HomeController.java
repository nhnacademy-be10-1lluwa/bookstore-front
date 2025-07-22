package com.nhnacademy.illuwa.common.controller;

import com.nhnacademy.illuwa.book.dto.BestSellerResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final BookService bookService;

    @GetMapping("/")
    public String home(Model model) {

        List<BestSellerResponse> bestSeller = bookService.getBestSellers();
        model.addAttribute("bestSeller",bestSeller);
        return "home";
    }
}