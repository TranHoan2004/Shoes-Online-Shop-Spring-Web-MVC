package com.HE180030.dto;

import com.HE180030.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuppliersDTO {
    private long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Category category;
}
