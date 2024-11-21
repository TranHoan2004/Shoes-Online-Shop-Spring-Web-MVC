package com.HE180030.repository;

import com.HE180030.model.Cart;

import java.util.List;

public interface CartRepository {
//    void insertCart(long accountID, long productID, int amount, String size);

    void updateAmountCart(long accountID, long productID, int amount);

    void deleteCartByProductID(long productID);

    void deleteCartByAccountID(long id);

    List<Cart> getCartByAccountID(long accountID);

//    String getAmountCart(String username);

    Cart getCartByAccountIdAndProductId(long accountID, long productID, String size);

    void createCart(long accountID, long productID, int amount, String size);

    void createAmountAndSizeCart(long accountID, long productID, int amount, String size);

    void editAmountAndSizeCart(long accountID, long productID, int amount, String size);

    void editAmountCart(long accountID, long productID, int amount);
}
