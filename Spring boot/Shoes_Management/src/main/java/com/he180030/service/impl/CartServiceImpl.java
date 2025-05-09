package com.HE180030.service.impl;

import com.HE180030.dto.CartDTO;
import com.HE180030.model.Cart;
import com.HE180030.repository.CartRepository;
import com.HE180030.service.CartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    private CartDTO convertToCartDTO(Cart cart) {
        return CartDTO.builder()
                .amount(cart.getAmount())
                .size(cart.getSize())
                .id(cart.getId())
                .build();
    }
}
