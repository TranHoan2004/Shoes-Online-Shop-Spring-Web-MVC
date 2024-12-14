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
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uID")
    private int id;

    @Column(name = "[user]", length = 10)
    private String username;

    @Column(name = "pass", length = 10)
    private String password;

    @Column(columnDefinition = "bit")
    private Boolean isSell;

    @Column(columnDefinition = "bit")
    private Boolean isAdmin;

    @Column(length = 50)
    private String email;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Cart> carts;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Product> products;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<TotalSalesTarget> totalSalesTargetList;
}
