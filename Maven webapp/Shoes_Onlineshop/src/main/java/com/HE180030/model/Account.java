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
    private long id;

    @Column(name = "user", length = 10, nullable = false)
    private String username;

    @Column(name = "pass", length = 10, nullable = false)
    private String password;

    private int isSell;
    private int isAdmin;

    @Column(length = 50)
    private String email;

    @OneToMany(mappedBy = "account")
    private List<Cart> carts;

    @OneToMany(mappedBy = "account")
    private List<Product> products;

    @OneToMany(mappedBy = "account")
    private List<Invoice> invoices;
}
