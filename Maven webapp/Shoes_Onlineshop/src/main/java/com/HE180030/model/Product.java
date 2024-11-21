package com.HE180030.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 200)
    private String name;

    @Column(length = 500)
    private String image;
    private double price;

    @Column(length = 500)
    private String title;
    private String description;

    @Column(length = 50)
    private String model;

    @Column(length = 50)
    private String color;

    @Column(length = 50)
    private String delivery;

    @Column(length = 500)
    private String image2;

    @Column(length = 500)
    private String image3;

    @Column(length = 500)
    private String image4;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cateid")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Cart> carts;

    @ManyToOne
    @JoinColumn(name = "sell_ID")
    private Account account;
}
