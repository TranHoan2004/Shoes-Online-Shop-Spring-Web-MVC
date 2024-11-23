package com.HE180030.service;

import com.HE180030.dto.CartDTO;
import com.HE180030.model.Cart;

import java.util.List;

public interface CartService {
    void updateAmountCartDTO(long accountID, long productID, int amount);

    void deleteCartDTOByProductID(long productID);

    void deleteCartDTOByAccountID(long id);

    List<CartDTO> getCartDTOByAccountID(long accountID);

    CartDTO getCartDTOByAccountIDAndProductID(long accountID, long productID, String size);

    void create(long accountID, long productID, int amount, String size);

    void createAmountAndSize(long accountID, long productID, int amount, String size);

    void editAmountAndSize(long accountID, long productID, int amount, String size);

    void editAmount(long accountID, long productID, int amount);
}
