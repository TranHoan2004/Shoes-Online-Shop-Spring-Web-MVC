package com.HE180030.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO extends IdAndName {
    private double totalPrice;
    private Date exportDate;
    private String context;
    private String typepay;
    private int phone;
    private String delivery;
}
