package com.HE180030.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 10)
    private String username;

    @Column(length = 10)
    private String password;

    private Boolean isSell;

    private Boolean isAdmin;

    @Column(length = 50)
    private String email;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Cart> carts;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Product> products;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Invoice> invoices;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<TotalSalesTarget> totalSalesTargetList;
}
