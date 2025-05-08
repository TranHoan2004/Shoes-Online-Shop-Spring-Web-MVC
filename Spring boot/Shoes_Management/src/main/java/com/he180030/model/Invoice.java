package com.HE180030.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maHD")
    private int id;

    @Column(name = "tongGia")
    private double totalPrice;

    @Column(name = "ngayXuat")
    private Date exportDate;

    @Transient
    private String context;

    @Transient
    private String typepay;

    @Transient
    private int phone;

    @Transient
    private String delivery;

    @Transient
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "accountID")
    private Account account;
}
