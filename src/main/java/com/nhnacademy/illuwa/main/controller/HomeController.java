package com.nhnacademy.illuwa.main.controller;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.book.dto.BestSellerResponse;
import com.nhnacademy.illuwa.book.dto.SearchBookResponse;
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
    private final ProductServiceClient productServiceClient;

    @GetMapping("/")
    public String home(Model model) {

        List<BestSellerResponse> bestSeller = productServiceClient.getBestSeller();
        model.addAttribute("bestSeller",bestSeller);
        return "home";
    }
}