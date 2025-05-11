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

    @GetMapping("/managerCart")
    public String manageCart(Model model, HttpSession session) {
//        AccountDTO account = (AccountDTO) session.getAttribute("account");
//        if (account == null) return "redirect:/login";
//        int id = account.getId();
//        List<CartDTO> cartDTOs = cartService.getCartDTOByAccountID(id);
//        List<ProductDTO> productDTOs = productService.getAllProductDTOs();
        displayContent(session, model, 0.1);
        return "cart";
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
        return "redirect:/manager-cart";
    }

    @GetMapping("/cart")
    public String redirectToCartScreen(Model model, HttpSession session) {
//        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
//        AtomicReference<Double> totalMoney = new AtomicReference<>((double) 0);
//        List<CartDTO> carts = manager.getCartDTOs(accountDTO.getId());
//        List<ProductDTO> products = manager.getProductDTOs();
//        carts.forEach(cartDTO -> products.forEach(productDTO -> {
//            if (cartDTO.getProductID() == productDTO.getId()) {
//                totalMoney.updateAndGet(v -> (v + (productDTO.getPrice() * cartDTO.getAmount())));
//            }
//        }));
//        double totalMoneyVAT = totalMoney.get() * 1.1;
//        model.addAttribute("totalMoney", totalMoney.get());
//        model.addAttribute("totalMoneyVAT", totalMoneyVAT);
//        model.addAttribute("listCarts", carts);
//        model.addAttribute("listProducts", manager.getProductDTOs());
        displayContent(session, model, 1.1);
        model.addAttribute("status", CartStatus.class.getEnumConstants());
        return "cart";
    }

    @GetMapping("/changeAmount")
    public String changeAmountCart(Model model, HttpSession session,
                                   @ModelAttribute("productID") int productID,
                                   @ModelAttribute("amount") int amount,
                                   @ModelAttribute("status") CartStatus status) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        if (accountDTO == null) {
            return "login";
        }
        int accountID = accountDTO.getId();
        if (status == CartStatus.Add) {
            amount++;
            model.addAttribute("mess", "Increased amount!");
        } else if (status == CartStatus.Sub) {
            amount--;
            model.addAttribute("mess", "Decreased amount!");
        }
        cartService.updateAmountCartDTO(accountID, productID, amount);
        return "redirect:/managerCart";
    }

    @GetMapping("/deleteCart")
    public String deleteCart(Model model, @ModelAttribute("id") int id) {
        cartService.deleteCartDTOByProductID(id);
        model.addAttribute("message", "Delete successfully!");
        return "cart";
    }

    @GetMapping("/totalMoneyCart")
    public String totalMoneyCart() {
        return null;
    }

    private void displayContent(@NotNull HttpSession session, @NotNull Model model, double number) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        AtomicReference<Double> totalMoney = new AtomicReference<>((double) 0);
        List<CartDTO> carts = manager.getCartDTOs(accountDTO.getId());
        List<ProductDTO> products = manager.getProductDTOs();
        carts.forEach(cartDTO -> products.forEach(productDTO -> {
            if (cartDTO.getProductID() == productDTO.getId()) {
                totalMoney.updateAndGet(v -> (v + (productDTO.getPrice() * cartDTO.getAmount())));
            }
        }));
        double totalMoneyVAT = totalMoney.get() * 1.1;
        model.addAttribute("totalMoney", totalMoney.get());
        model.addAttribute("totalMoneyVAT", totalMoneyVAT);
        model.addAttribute("listCarts", carts);
        model.addAttribute("listProducts", manager.getProductDTOs());
    }
}
