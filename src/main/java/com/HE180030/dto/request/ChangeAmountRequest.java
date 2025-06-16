package com.HE180030.dto.request;

import com.HE180030.enumerate.CartStatus;

public record ChangeAmountRequest(String productID,
                                  int amount,
                                  CartStatus status) {
    ;
}
