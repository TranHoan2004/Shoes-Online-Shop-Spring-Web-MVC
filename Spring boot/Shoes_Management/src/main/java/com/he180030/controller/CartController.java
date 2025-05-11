package com.HE180030.controller;

import com.HE180030.dto.AccountDTO;
import com.HE180030.dto.CartStatus;
import com.HE180030.dto.response.ApiResponse;
import com.HE180030.dto.response.CartResponse;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.service.CartService;
import com.HE180030.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/cart")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    ProductService pSrv;
    CartService cSrv;

    public CartController(CartService cartService, ProductService pSrv) {
        this.cSrv = cartService;
        this.pSrv = pSrv;
    }

    @GetMapping("/manage_cart")
    public ResponseEntity<?> manageCart(HttpSession session) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(displayContent(session, 0.1))
        );
    }

    /*Tested*/
    @GetMapping("/amount")
    public ResponseEntity<?> loadAmountCart(HttpSession session) {
        int totalAmountCart = 0;
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        if (accountDTO != null) {
            int accountID = accountDTO.getId();
            List<CartResponse> list = cSrv.getCartDTOByAccountID(accountID);
            totalAmountCart = list.size();
        }
        return ResponseEntity.ok(ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Total amount of cart is: " + totalAmountCart)
                .data(totalAmountCart)
                .build()
        );
    }

    @PostMapping("/add_to_cart")
    public ResponseEntity<?> addToCart(HttpSession session,
                                       @RequestBody int productID,
                                       @RequestBody int size) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        if (accountDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    ApiResponse.builder()
                            .code(HttpStatus.UNAUTHORIZED.value())
                            .message("session null, cannot add to cart")
            );
        }
        int accountID = accountDTO.getId();
        String message;

        CartResponse existedCart = cSrv.getCartDTOByAccountIDAndProductID(accountID, productID, size);
        if (existedCart != null) {
            int existedAmount = existedCart.getAmount();
            cSrv.createAmountAndSize(
                    accountID,
                    productID,
                    existedAmount > 5 ? (existedAmount + 1) : (existedAmount + 2),
                    size
            );
            message = "Number of products increase successfully";
        } else {
            cSrv.create(accountID, productID, 2, size);
            message = "Added product into cart";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .code(HttpStatus.CONTINUE.value())
                        .message("Redirect to manage cart")
                        .data(message)
        );
    }

    @GetMapping("")
    public ResponseEntity<?> getCart(HttpSession session) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(Map.of(
                                "content", displayContent(session, 1.1),
                                "status", CartStatus.class.getEnumConstants()
                        ))
        );
    }

    @PatchMapping("/change_amount")
    public ResponseEntity<?> changeAmountCart(HttpSession session,
                                              @RequestBody int productID,
                                              @RequestBody int amount,
                                              @RequestBody CartStatus status) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        if (accountDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    ApiResponse.builder()
                            .code(HttpStatus.UNAUTHORIZED.value())
                            .message("session null, cannot add to cart")
            );
        }
        int accountID = accountDTO.getId();
        String message = "";
        if (status == CartStatus.Add) {
            amount++;
            message = "Increased amount!";
        } else if (status == CartStatus.Sub) {
            amount--;
            message = "Decreased amount!";
        }
        cSrv.updateAmountCartDTO(accountID, productID, amount);
        return ResponseEntity.status(HttpStatus.CONTINUE).body(
                ApiResponse.builder()
                        .code(HttpStatus.CONTINUE.value())
                        .message(message)
        );
    }

    @DeleteMapping("/delete_cart")
    public ResponseEntity<?> deleteCart(@RequestBody int id) {
        cSrv.deleteCartDTOByProductID(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Delete successfully!")
        );
    }

    @GetMapping("/total_money_cart")
    public ResponseEntity<?> totalMoneyCart(HttpSession session) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        List<CartResponse> carts = cSrv.getCartDTOByAccountID(accountDTO.getId());
        List<ProductResponse> products = pSrv.getAllProducts();
        double totalMoney = 0;
        for (CartResponse c : carts) {
            for (ProductResponse p : products) {
                if (c.getProductId() == p.getId()) {
                    totalMoney = totalMoney + p.getPrice() * c.getAmount();
                }
            }
        }

        double totalMoneyVAT = totalMoney * 1.1;
        totalMoneyVAT = Math.round(totalMoneyVAT);
        return ResponseEntity.status(HttpStatus.CONTINUE).body(
                ApiResponse.builder()
                        .code(HttpStatus.CONTINUE.value())
                        .message(
                                "Total amount of VAT is: " + totalMoneyVAT +
                                        "\nTotal amount of money is: " + totalMoney
                        )
                        .data(Map.of(
                                "VAT", totalMoneyVAT,
                                "total money", totalMoneyVAT
                        ))
        );
    }

    private @NotNull @Unmodifiable Map<String, Object> displayContent(@NotNull HttpSession session, double number) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        AtomicReference<Double> totalMoney = new AtomicReference<>((double) 0);
        List<CartResponse> carts = cSrv.getCartDTOByAccountID(accountDTO.getId());
        List<ProductResponse> products = pSrv.getAllProducts();
        carts.forEach(cartDTO -> products.forEach(productDTO -> {
            if (cartDTO.getProductId() == productDTO.getId()) {
                totalMoney.updateAndGet(v -> (v + (productDTO.getPrice() * cartDTO.getAmount())));
            }
        }));
        double totalMoneyVAT = totalMoney.get() * number;
        return Map.of(
                "total money", totalMoney.get(),
                "total money VAT", totalMoneyVAT,
                "list of carts", carts,
                "list of products", products
        );
    }
}
