package com.HE180030.dto.request;

import com.HE180030.enumerate.CartStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeAmountRequest {
    int productID;
    int amount;
    CartStatus status;
}
