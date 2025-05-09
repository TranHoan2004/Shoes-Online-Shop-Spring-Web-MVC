package com.HE180030.controller;

import com.HE180030.dto.AccountDTO;
import com.HE180030.dto.response.ApiResponse;
import com.HE180030.dto.response.CartResponse;
import com.HE180030.service.CartService;
import com.HE180030.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
    }

    @GetMapping("/amount")
    public ResponseEntity<?> loadAmountCart(HttpSession session) {
        int totalAmountCart = 0;
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        if (accountDTO != null) {
            int accountID = accountDTO.getId();
            List<CartResponse> list = cartService.getCartDTOByAccountID(accountID);
            totalAmountCart = list.size();
        }
        return ResponseEntity.ok(ApiResponse.builder().code(HttpStatus.OK.value()).message("Total amount of cart is: " + totalAmountCart).data(totalAmountCart).build());
    }
}
