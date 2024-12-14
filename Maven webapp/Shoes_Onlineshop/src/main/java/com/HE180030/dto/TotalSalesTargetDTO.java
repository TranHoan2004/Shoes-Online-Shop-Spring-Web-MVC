package com.HE180030.dto;

import com.HE180030.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalSalesTargetDTO {
    private Account account;
    private float totalTarget;
    private float totalSales;
}
