package com.HE180030.controller;

import com.HE180030.dto.AccountDTO;
import com.HE180030.dto.CartDTO;
import com.HE180030.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/manage_cart")
    public String manageCart(Model model, HttpSession session) {
        return "manage-cart";
    }

    @GetMapping("/loadAllAmountCart")
    public ResponseEntity<Integer> loadAmountCart(HttpSession session) {
        int totalAmountCart = 0;
        // account
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        if (accountDTO != null) {
            int accountID = accountDTO.getId();
            List<CartDTO> list = cartService.getCartDTOByAccountID(accountID);
            totalAmountCart = list.size();
        }
        return ResponseEntity.ok(totalAmountCart);
    }

    @PostMapping("/addToCart")
    public String addToCart(Model model, HttpSession session,
                            @RequestParam("pid") int id) {
        return null;
    }
}
