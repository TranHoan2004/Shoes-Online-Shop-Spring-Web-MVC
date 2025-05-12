package com.HE180030.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {
    String id;
    String name;
    String image;
    String image2;
    String image3;
    String image4;
    String title;
    String model;
    String color;
    String delivery;
    String description;
    String categoryId;
}
