package com.HE180030.controller;

import com.HE180030.dto.request.ChangeAmountRequest;
import com.HE180030.dto.request.CreateCartRequest;
import com.HE180030.enumerate.CartStatus;
import com.HE180030.dto.request.DeleteRequest;
import com.HE180030.dto.response.ResponseWrapper;
import com.HE180030.dto.response.CartResponse;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.security.utils.SecurityUtils;
import com.HE180030.service.AccountService;
import com.HE180030.service.CartService;
import com.HE180030.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/c")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartController {
    ProductService pSrv;
    CartService cSrv;
    AccountService aSrv;
    Logger logger = LoggerFactory.getLogger(CartController.class);

    /**
     * Tested
     * <p><strong>Output:</strong></p>
     * <pre>
     *     {
     *     "code": 200,
     *     "message": "OK",
     *     "data": [
     *         {
     *             "id": "Mg==",
     *             "amount": 1000,
     *             "size": "1",
     *             "productId": "MTE="
     *         }
     *     ],
     *     "dataSize": 0
     * }
     * </pre>
     */
    @GetMapping("/cart")
    public ResponseEntity<?> getCartByUser() {
        logger.info("getCartByUser");
        int id = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        return returnResponseData(cSrv.getCartByAccountID(id), HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Tested
     * <p><strong>Output:</strong></p>
     * <pre>
     *     {
     *     "code": 200,
     *     "message": "OK",
     *     "data": {
     *         "list of products": [
     *             {
     *                 "id": "MTE=",
     *                 "image": "<a href="https://myshoes">...</a>.",
     *                 "name": "GI├ÇY NIKE AIR MAX AP NAM - X├üM XANH _ Baocao",
     *                 "title": "GI├ÇY NIKE AIR MAX AP NAM - X├üM XANH",
     *                 "price": 180.0,
     *                 "color": "Gray"
     *             },
     *             {
     *                 "id": "MTU=",
     *                 "image": "<a href="https://product.hsta">...</a>",
     *                 "name": "Adidas Ultraboost 4.0 -2",
     *                 "title": "Adidas Ultraboost 4.0",
     *                 "price": 156.0,
     *                 "color": "Blue"
     *             },
     *             ...rest
     *           ],
     *         "list of carts": [
     *             {
     *                 "id": "Mg==",
     *                 "amount": 1000,
     *                 "size": "1",
     *                 "productId": "MTE="
     *             }
     *         ],
     *         "total money VAT": 18000.0,
     *         "total money": 180000.0
     *     },
     *     "dataSize": 0
     * }
     * </pre>
     */
    @GetMapping("/manage")
    public ResponseEntity<?> manageCart() {
        logger.info("manageCart");
        return returnResponseData(displayContent(0.1), HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Tested
     * <p><strong>Request with bearer token</strong></p>
     * <p><strong>Output:</strong></p>
     * <pre>
     *     {
     *     "code": 200,
     *     "message": "OK",
     *     "data": {
     *         "list of products": [
     *             {
     *                 "id": "MTE=",
     *                 "image": "<a href="https://myshoes">...</a>.",
     *                 "name": "GI├ÇY NIKE AIR MAX AP NAM - X├üM XANH _ Baocao",
     *                 "title": "GI├ÇY NIKE AIR MAX AP NAM - X├üM XANH",
     *                 "price": 180.0,
     *                 "color": "Gray"
     *             },
     *             {
     *                 "id": "MTU=",
     *                 "image": "<a href="https://product.hsta">...</a>",
     *                 "name": "Adidas Ultraboost 4.0 -2",
     *                 "title": "Adidas Ultraboost 4.0",
     *                 "price": 156.0,
     *                 "color": "Blue"
     *             },
     *             ...rest
     *           ],
     *         "list of carts": [
     *             {
     *                 "id": "Mg==",
     *                 "amount": 1000,
     *                 "size": "1",
     *                 "productId": "MTE="
     *             }
     *         ],
     *         "total money VAT": 18000.0,
     *         "total money": 180000.0
     *     },
     *     "dataSize": 0
     * }
     * </pre>
     */
    @GetMapping("/amount")
    public ResponseEntity<?> loadAmountCart() {
        logger.info("loadAmountCart");
        int accountID = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        List<CartResponse> list = cSrv.getCartByAccountID(accountID);
        int totalAmountCart = list.size();
        return returnResponseData(totalAmountCart, HttpStatus.OK.value(), "Total amount of cart is: " + totalAmountCart);
    }

    /**
     * Tested
     * <p><strong>Request:</strong></p>
     * <pre>
     * {
     *     "productID": "11",
     *     "size": 3
     * }
     * </pre>
     * <p><strong>Response:</strong></p>
     * <pre>
     * {
     *     "code": 100,
     *     "message": "Redirect to manage cart",
     *     "data": "Number of products increase successfully",
     *     "dataSize": 0
     * }
     * </pre>
     */
    @PostMapping("/add_to_cart")
    public ResponseEntity<?> addToCart(@RequestBody CreateCartRequest request) {
        logger.info("addToCart");
        int accountID = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        String message;

        CartResponse existedCart = cSrv.getCartByAccountIDAndProductID(accountID, request.productID());
        if (existedCart != null) {
            int existedAmount = existedCart.getAmount();
            cSrv.updateCart(accountID, request.productID(), existedAmount + request.amount());
            message = "Number of products increase successfully";
        } else {
            cSrv.create(accountID, request.productID(), request.amount());
            message = "Added product into cart";
        }
        return returnResponseData(message, HttpStatus.CONTINUE.value(), "Redirect to manage cart");
    }

    /**
     * Tested
     * <p><strong>Response:</strong></p>
     * <pre>
     * {
     *     "code": 200,
     *     "message": "OK",
     *     "data": {
     *         "content": {
     *             "list of products": [
     *                 {
     *                     "id": "MTE=",
     *                     "image": "<a href="https://myshoes.vn/image/ca...">...</a>",
     *                     "name": "GI├ÇY NIKE AIR MAX AP NAM - X├üM XANH _ Baocao",
     *                     "title": "GI├ÇY NIKE AIR MAX AP NAM - X├üM XANH",
     *                     "price": 180.0,
     *                     "color": "Gray"
     *                 },
     *                 {
     *                     "id": "MTU=",
     *                     "image": "<a href="https://product.hstatic.net/10002...">...</a>",
     *                     "name": "Adidas Ultraboost 4.0 -2",
     *                     "title": "Adidas Ultraboost 4.0",
     *                     "price": 156.0,
     *                     "color": "Blue"
     *                 },
     *                 ...rest
     *             ],
     *             "total money": 180000.0,
     *             "total money VAT": 198000.00000000003,
     *             "list of carts": [
     *                 {
     *                     "id": "Mg==",
     *                     "amount": 1000,
     *                     "size": "1",
     *                     "productId": "MTE="
     *                 }
     *             ]
     *         },
     *         "status": [
     *             "Sub",
     *             "Add"
     *         ]
     *     },
     *     "dataSize": 0
     * }
     * </pre>
     */
    @GetMapping("/")
    public ResponseEntity<?> getCart() {
        logger.info("getCart");
        return returnResponseData(Map.of("content", displayContent(1.1), "status", CartStatus.class.getEnumConstants()), HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Tested
     * <p><strong>Request:</strong></p>
     * <pre>
     * {
     *     "productID": 11,
     *     "amount": 2,
     *     "status": "Sub"
     * }
     * </pre>
     * <p><strong>Response:</strong></p>
     * <pre>
     * {
     *     "code": 100,
     *     "message": "Decreased amount!",
     *     "dataSize": 0
     * }
     * </pre>
     */
    @PatchMapping("/change_amount")
    public ResponseEntity<?> changeAmountCart(@RequestBody ChangeAmountRequest request) {
        logger.info("changeAmountCart");
        int accountID = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        String message = "";
        int amount = request.amount();
        if (request.status() == CartStatus.Add) {
            message = "Increased amount!";
        } else if (request.status() == CartStatus.Sub) {
            amount = -amount;
            message = "Decreased amount!";
        }
        cSrv.updateAmountCart(accountID, request.productID(), amount);
        return returnResponseData(null, HttpStatus.CONTINUE.value(), message);
    }

    /**
     * Tested
     * <p><strong>Request:</strong></p>
     * <pre>
     * {
     *     "code": 101,
     *     "id": 11
     * }
     * </pre>
     * <p><strong>Response:</strong></p>
     * <pre>
     * {
     *     "code": 200,
     *     "message": "Delete successfully!",
     *     "dataSize": 0
     * }
     * </pre>
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCartByAccount(@RequestBody DeleteRequest request) {
        logger.info("delete");
        int id = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        try {
            switch (request.code()) {
                case 100 -> {
                    logger.info("delete cart by account");
                    cSrv.deleteCartByAccountID(id);
                }
                case 101 -> {
                    logger.info("delete cart by product");
                    cSrv.deleteCartByAccountIDAndProductID(id, request.id());
                }
            }
            return returnResponseData(null, HttpStatus.OK.value(), "Delete successfully!");
        } catch (Exception e) {
            logger.error("delete cart by account ID and product ID: {}", e.getMessage());
            return returnResponseData(null, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    /**
     * Tested
     * <p><strong>Response:</strong></p>
     * <pre>
     * {
     *     "code": 100,
     *     "message": "Total amount of VAT is: 911.0\nTotal amount of money is: 828.0",
     *     "data": {
     *         "VAT": 911.0,
     *         "total money": 911.0
     *     },
     *     "dataSize": 0
     * }
     * </pre>
     */
    @GetMapping("/total_money")
    public ResponseEntity<?> totalMoneyCart() {
        logger.info("totalMoneyCart");
        int id = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        List<CartResponse> carts = cSrv.getCartByAccountID(id);
        List<ProductResponse> products = pSrv.getAllProducts();
        double totalMoney = 0;
        for (CartResponse c : carts) {
            for (ProductResponse p : products) {
                if (Objects.equals(c.getProductId(), p.getId())) {
                    totalMoney = totalMoney + p.getPrice() * c.getAmount();
                }
            }
        }

        double totalMoneyVAT = totalMoney * 1.1;
        totalMoneyVAT = Math.round(totalMoneyVAT);
        return returnResponseData(Map.of("VAT", totalMoneyVAT, "total money", totalMoneyVAT), HttpStatus.CONTINUE.value(), "Total amount of VAT is: " + totalMoneyVAT + "\nTotal amount of money is: " + totalMoney);
    }

    private @NotNull @Unmodifiable Map<String, Object> displayContent(double number) {
        AtomicReference<Double> totalMoney = new AtomicReference<>((double) 0);
        int id = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        List<CartResponse> carts = cSrv.getCartByAccountID(id);
        List<ProductResponse> products = pSrv.getAllProducts();
        carts.forEach(cartDTO -> products.forEach(productDTO -> {
            if (Objects.equals(cartDTO.getProductId(), productDTO.getId())) {
                totalMoney.updateAndGet(v -> (v + (productDTO.getPrice() * cartDTO.getAmount())));
            }
        }));
        double totalMoneyVAT = totalMoney.get() * number;
        return Map.of("total money", totalMoney.get(), "total money VAT", totalMoneyVAT, "list of carts", carts, "list of products", products);
    }

    private ResponseEntity<?> returnResponseData(Object result, int code, String message) {
        return ResponseEntity.ok(ResponseWrapper.builder().data(result).code(code).message(message).build());
    }
}
