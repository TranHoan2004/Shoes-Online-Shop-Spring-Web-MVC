package com.HE180030.controller;

import com.HE180030.dto.AccountDTO;
import com.HE180030.dto.CartDTO;
import com.HE180030.service.CartService;
import com.HE180030.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/manage_cart")
    public String manageCart(Model model, HttpSession session) {
        AccountDTO account = (AccountDTO) session.getAttribute("account");

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
                            @RequestParam("id") int productID, HttpServletRequest request) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        if (accountDTO == null) {
            return "login";
        }
        int accountID = accountDTO.getId();
        int amount = 2;

        String size = request.getParameter("size");
        CartDTO existedCart = cartService.getCartDTOByAccountIDAndProductID(accountID, productID, size);
        int existedAmount;
        if (existedCart != null) {
            existedAmount = existedCart.getAmount();
            if (existedAmount > 5) {
                cartService.createAmountAndSize(accountID, productID, (existedAmount + 1), size);
            } else {
                cartService.createAmountAndSize(accountID, productID, (existedAmount + amount), size);
            }
            model.addAttribute("message", "Number of products increase successfully");
        } else {
            cartService.create(accountID, productID, amount, size);
            model.addAttribute("message", "Added product into cart");
        }
        return "manager-cart";
    }
}
