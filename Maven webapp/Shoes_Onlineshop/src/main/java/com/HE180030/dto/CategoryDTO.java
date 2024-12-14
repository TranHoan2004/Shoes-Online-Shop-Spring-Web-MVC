package com.HE180030.dto;

import com.HE180030.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private long id;
    private String name;
    private List<Product> products;
}
