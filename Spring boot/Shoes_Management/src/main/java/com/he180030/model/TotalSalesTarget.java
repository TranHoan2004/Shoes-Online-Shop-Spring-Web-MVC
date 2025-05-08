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
@Table(name = "TongChiTieuBanHang")
public class TotalSalesTarget {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    private Account account;

    @Column(name = "TongChiTieu")
    private float totalTarget;

    @Column(name = "TongBanHang")
    private float totalSales;
}
