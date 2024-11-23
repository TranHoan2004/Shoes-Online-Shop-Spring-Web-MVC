package com.HE180030.dto;

import com.HE180030.model.Cart;
import com.HE180030.model.Invoice;
import com.HE180030.model.Product;

import java.util.List;

public class AccountDTO {
    private long id;
    private String username;
    private String password;
    private int isSell;
    private int isAdmin;
    private String email;
    private List<Cart> carts;
    private List<Product> products;
    private List<Invoice> invoices;
}
