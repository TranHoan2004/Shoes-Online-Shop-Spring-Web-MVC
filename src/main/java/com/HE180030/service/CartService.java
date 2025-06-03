package com.HE180030.service;

import com.HE180030.dto.response.CartResponse;

import java.util.List;

public interface CartService {
    List<CartResponse> getCartByAccountID(int accountId);

    CartResponse getCartByAccountIDAndProductID(int accountID, int productID, int size);

    void createAmountAndSize(int accountID, int productID, int amount, int size);

    void create(int accountID, int productID, int amount, int size);

    void updateAmountCart(int accountID, int productID, int amount);

    void deleteCart(int productID);

    void deleteCartByProductID(int productID);

    void deleteCartByAccountID(int id);
}
