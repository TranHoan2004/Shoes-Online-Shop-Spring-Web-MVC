package com.HE180030.dto.request;

public record AddProductRequest(
        String name,
        String image,
        double price,
        String title,
        String description,
        int category,
        int sellId,
        String model,
        String color,
        String delivery,
        String image2,
        String image3,
        String image4) {
}
