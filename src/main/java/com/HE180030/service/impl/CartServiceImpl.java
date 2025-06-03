package com.HE180030.service.impl;

import com.HE180030.dto.response.CartResponse;
import com.HE180030.model.Cart;
import com.HE180030.repository.CartRepository;
import com.HE180030.service.CartService;
import com.HE180030.utils.UrlIdEncoder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    CartRepository repo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public List<CartResponse> getCartByAccountID(int accountId) {
        return repo.getByAccountId(accountId)
                .stream().map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CartResponse getCartByAccountIDAndProductID(int accountID, int productID, int size) {
        return null;
    }

    @Override
    public void createAmountAndSize(int accountID, int productID, int amount, int size) {

    }

    @Override
    public void create(int accountID, int productID, int amount, int size) {

    }

    @Override
    public void updateAmountCart(int accountID, int productID, int amount) {

    }

    @Override
    public void deleteCart(int productID) {

    }

    @Override
    public void deleteCartByProductID(int productID) {

    }

    @Override
    public void deleteCartByAccountID(int id) {
        logger.info("Deleting cart with id " + id);
        List<Cart> carts = repo.getByAccountId(id);
        carts.forEach(cart -> {
            cart.setAccount(null);
            cart.setProduct(null);
            repo.delete(cart);
        });
    }

    private CartResponse convert(@NotNull Cart cart) {
        return CartResponse.builder()
                .amount(cart.getAmount())
                .size(cart.getSize())
                .id(UrlIdEncoder.encodeId(cart.getId()))
                .build();
    }
}
