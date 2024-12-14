package com.HE180030.service;

import com.HE180030.dto.CartDTO;
import com.HE180030.model.Cart;

import java.util.List;

public interface CartService {
    void updateAmountCartDTO(int accountID, int productID, int amount);

    void deleteCartDTOByProductID(int productID);

    void deleteCartDTOByAccountID(int id);

    List<CartDTO> getCartDTOByAccountID(int accountID);

    CartDTO getCartDTOByAccountIDAndProductID(int accountID, int productID, String size);

    void create(int accountID, int productID, int amount, String size);

    void createAmountAndSize(int accountID, int productID, int amount, String size);

    void editAmountAndSize(int accountID, int productID, int amount, String size);

    void editAmount(int accountID, int productID, int amount);
}
