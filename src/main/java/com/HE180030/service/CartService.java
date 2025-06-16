package com.HE180030.service;

import com.HE180030.dto.response.CartResponse;

import java.util.List;

public interface CartService {
    List<CartResponse> getCartByAccountID(int accountId);

    CartResponse getCartByAccountIDAndProductID(int accountID, String productID);

    void create(int accountID, String productID, int amount);

    void updateAmountCart(int accountID, String productID, int amount);

    void updateCart(int accountID, String productID, int amount);

    void deleteCart(String productID);

    void deleteCartByAccountIDAndProductID(int accountID, String productID) throws Exception;

    void deleteCartByAccountID(int id);
}
