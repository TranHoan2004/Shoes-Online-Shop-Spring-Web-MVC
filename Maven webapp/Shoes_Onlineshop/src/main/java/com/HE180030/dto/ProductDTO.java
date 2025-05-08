package com.HE180030.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO extends IdAndName {
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
    private int categoryID;
}