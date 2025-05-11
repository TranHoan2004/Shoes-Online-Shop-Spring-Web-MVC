package com.HE180030.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateInvoiceRequest {
    int accountID;
    String context;
    double totalPrice;
    Date exportDate;
    String typePay;
    int phone;
    String delivery;
}
