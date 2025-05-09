package com.HE180030.service;

import com.HE180030.dto.CartDTO;
import com.HE180030.dto.response.CartResponse;

import java.util.List;

public interface CartService {
    List<CartResponse> getCartDTOByAccountID(int accountId);
}
