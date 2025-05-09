package com.HE180030.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
//    private final ProductService productService;
//    private final CategoryService categoryService;
//
//    public HomeController(ProductService productService, CategoryService categoryService) {
//        this.productService = productService;
//        this.categoryService = categoryService;
//    }

    @GetMapping("/")
    public ResponseEntity<String> init() {
        return ResponseEntity.status(HttpStatus.CONTINUE).body("Ready to init");
    }
}
