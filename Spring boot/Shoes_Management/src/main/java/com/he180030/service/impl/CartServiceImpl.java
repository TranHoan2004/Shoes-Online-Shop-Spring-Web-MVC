package com.HE180030.service.impl;

import com.HE180030.dto.response.CartResponse;
import com.HE180030.model.Cart;
import com.HE180030.repository.CartRepository;
import com.HE180030.service.CartService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository repo;

    public CartServiceImpl(CartRepository cartRepository) {
        this.repo = cartRepository;
    }

    @Override
    public List<CartResponse> getCartDTOByAccountID(int accountId) {
        return repo.getByAccountId(accountId)
                .stream().map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CartResponse getCartDTOByAccountIDAndProductID(int accountID, int productID, int size) {
        return null;
    }

    @Override
    public void createAmountAndSize(int accountID, int productID, int amount, int size) {

    }

    @Override
    public void editAmountAndSize(int accountID, int productID, int amount, int size) {

    }

    @Override
    public void editAmount(int accountID, int productID, int amount) {

    }

    @Override
    public void create(int accountID, int productID, int amount, int size) {

    }

    @Override
    public void updateAmountCartDTO(int accountID, int productID, int amount) {

    }

    @Override
    public void deleteCartDTOByProductID(int productID) {

    }

    private CartResponse convert(@NotNull Cart cart) {
        return CartResponse.builder()
                .amount(cart.getAmount())
                .size(cart.getSize())
                .id(cart.getId())
                .build();
    }
}
