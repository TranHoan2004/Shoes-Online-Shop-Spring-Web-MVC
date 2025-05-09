package com.HE180030.controller;

import com.HE180030.controller.utils.CartAndProductManager;
import com.HE180030.dto.AccountDTO;
import com.HE180030.dto.CartDTO;
import com.HE180030.dto.CartStatus;
import com.HE180030.dto.ProductDTO;
import com.HE180030.service.CartService;
import com.HE180030.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class CartController {
    private final CartService cartService;
    //    private final ProductService productService;
    private final CartAndProductManager manager;

    public CartController(CartService cartService, ProductService productService, CartAndProductManager manager) {
        this.cartService = cartService;
//        this.productService = productService;
        this.manager = manager;
    }
}
