package com.HE180030.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSupplier")
    private int id;

    @Column(name = "nameSupplier", length = 100)
    private String name;

    @Column(name = "phoneSupplier", length = 50)
    private String phone;

    @Column(name = "emailSupplier", length = 200)
    private String email;

    @Column(name = "addressSupplier", length = 200)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cateID")
    private Category category;
}
