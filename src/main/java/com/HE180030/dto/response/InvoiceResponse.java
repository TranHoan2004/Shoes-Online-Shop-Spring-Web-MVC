package com.HE180030.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceResponse {
    String id;
    String context;
    String phone;
    String delivery;
    double totalPrice;
    LocalDate exportDate;
}
