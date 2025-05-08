package com.HE180030.controller.utils;

import com.HE180030.dto.CartDTO;
import com.HE180030.dto.ProductDTO;
import com.HE180030.service.CartService;
import com.HE180030.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartAndProductManager {
    private final ProductService productService;
    private final CartService cartService;

    public CartAndProductManager(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    public List<CartDTO> getCartDTOs(int id) {
        return cartService.getCartDTOByAccountID(id);
    }

    public List<ProductDTO> getProductDTOs() {
        return productService.getAllProductDTOs();
    }
}
