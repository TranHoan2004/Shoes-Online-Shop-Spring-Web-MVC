package com.HE180030.repository;

import com.HE180030.model.Cart;

import java.util.List;

public interface CartRepository {
    void insertCart(int accountID, int productID, int amount, String size);

    void deleteCartByProductID(String productID);

    void deleteCartByAccountID(int id);

    List<Cart> getCartByAccountID(int accountID);

    String getAmountCart(String username);

    void updateAmountCart(int accountID, int productID, int amount);

    void deleteCart(int productID);

    void createCart(int accountID, int productID, int amount, String size);

    void createAmountAndSizeCart(int accountID, int productID, int amount, String size);

    Cart getCart(int accountID, int productID, String size);
}
