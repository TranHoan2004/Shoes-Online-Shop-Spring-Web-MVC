package com.HE180030.dto;

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
    private int id;
    private double totalPrice;
    private Date exportDate;
    private String context;
    private String typepay;
    private int phone;
    private String delivery;
    private String name;
}
