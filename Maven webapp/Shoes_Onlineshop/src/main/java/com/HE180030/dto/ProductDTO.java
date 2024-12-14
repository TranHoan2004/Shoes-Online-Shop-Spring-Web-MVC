package com.HE180030.dto;

import com.HE180030.model.Account;
import com.HE180030.model.Cart;
import com.HE180030.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private long id;
    private String name;
    private String image;
    private double price;
    private String title;
    private String description;
    private String model;
    private String color;
    private String delivery;
    private String image2;
    private String image3;
    private String image4;
    private long categoryID;
    private long accountID;
}
