package com.HE180030.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddProductRequest {
    String name;
    String image;
    double price;
    String title;
    String description;
    int category;
    int sellId;
    String model;
    String color;
    String delivery;
    String image2;
    String image3;
    String image4;
}
