package com.HE180030.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int id;
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
}
