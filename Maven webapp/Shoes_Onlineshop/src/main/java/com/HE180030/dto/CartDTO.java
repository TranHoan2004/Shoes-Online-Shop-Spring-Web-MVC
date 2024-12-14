package com.HE180030.dto;

import com.HE180030.model.Account;
import com.HE180030.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private long id;
    private Account account;
    private Product product;
    private int amount;
    private String size;
}
