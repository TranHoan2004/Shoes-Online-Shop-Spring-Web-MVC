package com.HE180030.repository;

import com.HE180030.model.Cart;

import java.util.List;

public interface CartRepository {
    void updateAmountCart(long accountID, long productID, int amount);

    void deleteByProductID(long productID);

    void deleteByAccountID(long id);

    List<Cart> getByAccountID(long accountID);

    Cart getByAccountIDAndProductID(long accountID, long productID, String size);

    void create(long accountID, long productID, int amount, String size);

    void createAmountAndSize(long accountID, long productID, int amount, String size);

    void editAmountAndSize(long accountID, long productID, int amount, String size);

    void editAmount(long accountID, long productID, int amount);
}
