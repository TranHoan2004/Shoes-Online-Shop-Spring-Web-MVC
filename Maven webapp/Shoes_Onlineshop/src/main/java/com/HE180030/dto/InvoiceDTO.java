package com.HE180030.dto;

import com.HE180030.model.Account;

import java.util.Date;

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
