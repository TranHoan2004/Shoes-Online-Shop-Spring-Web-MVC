package com.HE180030.repository;

import com.HE180030.model.Cart;

import java.util.List;

public interface CartRepository {
    void updateAmountCart(int accountID, int productID, int amount);

    void deleteByProductID(int productID);

    void deleteByAccountID(int id);

    List<Cart> getByAccountID(int accountID);

    Cart getByAccountIDAndProductID(int accountID, int productID, String size);

    void create(int accountID, int productID, int amount, String size);

    void createAmountAndSize(int accountID, int productID, int amount, String size);

    void editAmountAndSize(int accountID, int productID, int amount, String size);

    void editAmount(int accountID, int productID, int amount);
}
