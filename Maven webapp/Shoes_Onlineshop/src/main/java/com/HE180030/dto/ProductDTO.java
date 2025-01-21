package com.HE180030.dto;

import lombok.*;

@Builder
@Getter
@Setter
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

    @Override
    public String toString() {
        return "id=" + id +
               ", name='" + name + '\'';
    }
}
