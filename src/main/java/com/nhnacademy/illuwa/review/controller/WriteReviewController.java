package com.nhnacademy.illuwa.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WriteReviewController {
    @GetMapping("/write_review/{isbn}")
    public String writeReview(@PathVariable String isbn, Model model) {
        model.addAttribute("isbn", isbn);
        return "review/write_review";
    }
}
