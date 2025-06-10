package com.HE180030.service;

import com.HE180030.dto.response.CartResponse;

import java.util.List;

public interface CartService {
    List<CartResponse> getCartByAccountID(int accountId);

    CartResponse getCartByAccountIDAndProductID(int accountID, int productID);

    void create(int accountID, int productID, int amount);

    void updateAmountCart(int accountID, int productID, int amount);

    void updateCart(int accountID, int productID, int amount);

    void deleteCart(int productID);

    void deleteCartByAccountIDAndProductID(int accountID, int productID) throws Exception;

    void deleteCartByAccountID(int id);
}
