package com.HE180030.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/manage_product")
    public String manageProduct(Model model, HttpSession session) {
        return "manage-product";
    }
}
