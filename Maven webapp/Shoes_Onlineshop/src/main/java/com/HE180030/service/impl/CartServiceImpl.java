package com.HE180030.service.impl;

import com.HE180030.dto.CartDTO;
import com.HE180030.repository.CartRepository;
import com.HE180030.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void create(long accountID, long productID, int amount, String size) {

    }

    @Override
    public void updateAmountCartDTO(long accountID, long productID, int amount) {

    }

    @Override
    public void deleteCartDTOByProductID(long productID) {

    }

    @Override
    public void deleteCartDTOByAccountID(long id) {

    }

    @Override
    public List<CartDTO> getCartDTOByAccountID(long accountID) {
        return null;
    }

    @Override
    public CartDTO getCartDTOByAccountIDAndProductID(long accountID, long productID, String size) {
        return null;
    }

    @Override
    public void createAmountAndSize(long accountID, long productID, int amount, String size) {

    }

    @Override
    public void editAmountAndSize(long accountID, long productID, int amount, String size) {

    }

    @Override
    public void editAmount(long accountID, long productID, int amount) {

    }
}
