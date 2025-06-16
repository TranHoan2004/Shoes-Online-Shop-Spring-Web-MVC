package com.HE180030.dto.request;

public record CreateInvoiceRequest (double totalPrice,
                                    String exportDate,
                                    String typePay,
                                    String phone,
                                    String delivery) {
}
