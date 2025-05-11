package com.HE180030.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    int id;
    String name;
    String image;
    String image2;
    String image3;
    String image4;
    String model;
    String color;
    String delivery;
    double price;
    String title;
    String description;
    int category;
    String customCategory;
}
