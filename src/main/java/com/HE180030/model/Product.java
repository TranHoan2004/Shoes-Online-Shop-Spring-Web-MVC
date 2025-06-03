package com.HE180030.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(exclude = {"carts", "quantitiesSold"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(length = 200)
    String name;

    @Column(length = 500)
    String image;

    double price;

    @Column(length = 500)
    String title;

    @Column(columnDefinition = "LONGTEXT")
    String description;

    @Column(length = 50)
    String model;

    @Column(length = 50)
    String color;

    @Column(length = 50)
    String delivery;

    @Column(length = 500)
    String image2;

    @Column(length = 500)
    String image3;

    @Column(length = 500)
    String image4;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cate_id")
    Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    List<Cart> carts;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    List<Review> reviews;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    List<QuantitesSold> quantitiesSold;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sell_id")
    Account account;
}
