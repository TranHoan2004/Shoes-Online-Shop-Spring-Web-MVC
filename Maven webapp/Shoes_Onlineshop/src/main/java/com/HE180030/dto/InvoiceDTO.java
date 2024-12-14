package com.HE180030.dto;

import com.HE180030.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    private long id;
    private double totalPrice;
    private Date exportDate;
    private String context;
    private String typepay;
    private int phone;
    private String delivery;
    private String name;
    private Account account;
}
