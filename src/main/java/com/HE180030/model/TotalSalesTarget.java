package com.HE180030.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "total_sales_target", schema = "shoes_onlineshopping")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalSalesTarget {
    @Id
    @Column(name = "user_id", nullable = false)
    Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    Account account;

    @NotNull
    @Column(name = "total_sales", nullable = false)
    Float totalSales;

    @NotNull
    @Column(name = "total_target", nullable = false)
    Float totalTarget;

}