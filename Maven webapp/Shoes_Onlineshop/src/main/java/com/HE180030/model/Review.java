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
@Table(name = "Review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maReview")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productID")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountID")
    private Account account;

    @Column(length = 500)
    private String contentReview;
    private Date dateReview;
}
