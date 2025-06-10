package com.HE180030.service.impl;

import com.HE180030.dto.response.CartResponse;
import com.HE180030.model.Account;
import com.HE180030.model.Cart;
import com.HE180030.model.Product;
import com.HE180030.model.CartId;
import com.HE180030.repository.AccountRepository;
import com.HE180030.repository.CartRepository;
import com.HE180030.repository.ProductRepository;
import com.HE180030.service.CartService;
import com.HE180030.utils.UrlIdEncoder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    CartRepository repo;
    ProductRepository prodRepo;
    AccountRepository accRepo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public List<CartResponse> getCartByAccountID(int accountId) {
        logger.info("getCartByAccountID");
        return repo.getByAccountId(accountId)
                .stream().map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CartResponse getCartByAccountIDAndProductID(int accountID, int productID) {
        logger.info("getCartByAccountIDAndProductID");
//        int pID = UrlIdEncoder.decodeId(productID);
        Cart c = repo.findByAccountIdAndProductId(accountID, productID);
        if (c != null) {
            return CartResponse.builder()
                    .accountId(UrlIdEncoder.encodeId(String.valueOf(accountID)))
                    .productId(UrlIdEncoder.encodeId(String.valueOf(productID)))
                    .amount(c.getAmount())
                    .build();
        }
        return null;
    }

    @Override
    public void create(int accountID, int productID, int amount) {
        logger.info("create");
//        int pID = UrlIdEncoder.decodeId(productID);
        Product p = prodRepo.findById(productID).orElseThrow(() -> new RuntimeException("Product not found"));
        Account a = accRepo.findById(accountID).orElseThrow(() -> new RuntimeException("Account not found"));
        Cart c = repo.findByAccountIdAndProductId(accountID, productID);
        if (c != null) {
            int currentAmount = c.getAmount();
            c.setAmount(currentAmount + amount);
        } else {
            c = Cart.builder()
                    .id(CartId.builder()
                            .accountId(accountID)
                            .productId(productID)
                            .build())
                    .account(a)
                    .product(p)
                    .amount(amount)
                    .build();
        }
        repo.save(c);
    }

    @Override
    public void updateAmountCart(int accountID, int productID, int amount) {
        logger.info("updateAmountCart");
//        int pID = UrlIdEncoder.decodeId(productID);
        Cart cart = repo.findByAccountIdAndProductId(accountID, productID);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        int cartAmount = cart.getAmount();
        cart.setAmount(cartAmount + amount);
        repo.save(cart);
    }

    @Override
    public void updateCart(int accountID, int productID, int amount) {
        logger.info("updateCart");
        Cart cart = repo.findByAccountIdAndProductId(accountID, productID);
        cart.setAmount(amount);
        repo.save(cart);
    }

    @Override
    public void deleteCart(int productID) {

    }

    @Override
    public void deleteCartByAccountIDAndProductID(int accountID, int productID) throws Exception {
        logger.info("deleteCartByAccountIDAndProductID");
        Cart cart = repo.findByAccountIdAndProductId(accountID, productID);
        if (cart != null) {
            cart.setAccount(null);
            cart.setProduct(null);
            repo.delete(cart);
        } else {
            throw new Exception("Cart not found for account ID: " + accountID + " and product ID: " + productID);
        }
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
                .accountId(UrlIdEncoder.encodeId(String.valueOf(cart.getId().getAccountId())))
                .productId(UrlIdEncoder.encodeId(String.valueOf(cart.getId().getProductId())))
                .build();
    }
}
