package com.HE180030.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "invoice", schema = "shoes_onlineshopping")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    @Id
    @Column(name = "id", nullable = false)
    Integer id;

    @Column(name = "export_date")
    LocalDate exportDate;

    @NotNull
    @Column(name = "total_price", nullable = false)
    Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    Account account;

}