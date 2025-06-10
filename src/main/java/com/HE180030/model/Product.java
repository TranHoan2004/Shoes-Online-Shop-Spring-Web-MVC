package com.HE180030.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "product", schema = "shoes_onlineshopping")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    Integer id;

    @Size(max = 50)
    @Column(name = "color", length = 50)
    String color;

    @Size(max = 50)
    @Column(name = "delivery", length = 50)
    String delivery;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    String description;

    @Size(max = 500)
    @Column(name = "image", length = 500)
    String image;

    @Size(max = 500)
    @Column(name = "image2", length = 500)
    String image2;

    @Size(max = 500)
    @Column(name = "image3", length = 500)
    String image3;

    @Size(max = 500)
    @Column(name = "image4", length = 500)
    String image4;

    @Size(max = 50)
    @Column(name = "model", length = 50)
    String model;

    @Size(max = 200)
    @Column(name = "name", length = 200)
    String name;

    @NotNull
    @Column(name = "price", nullable = false)
    Double price;

    @Size(max = 500)
    @Column(name = "title", length = 500)
    String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sell_id")
    Account sell;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cate_id")
    Category cate;

}