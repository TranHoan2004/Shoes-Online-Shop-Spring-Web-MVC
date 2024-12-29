package com.HE180030.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuppliersDTO {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;
}
