package com.HE180030.service;

import com.HE180030.dto.response.CartResponse;

import java.util.List;

public interface CartService {
    List<CartResponse> getCartDTOByAccountID(int accountId);

    CartResponse getCartDTOByAccountIDAndProductID(int accountID, int productID, int size);

    void createAmountAndSize(int accountID, int productID, int amount, int size);

    void editAmountAndSize(int accountID, int productID, int amount, int size);

    void editAmount(int accountID, int productID, int amount);

    void create(int accountID, int productID, int amount, int size);

    void updateAmountCartDTO(int accountID, int productID, int amount);

    void deleteCartDTOByProductID(int productID);
}
