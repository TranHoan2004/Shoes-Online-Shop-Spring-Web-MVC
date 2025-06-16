package com.HE180030.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "supplier", schema = "shoes_onlineshopping")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {
    @Id
    @Column(name = "id", nullable = false)
    Integer id;

    @Size(max = 200)
    @Column(name = "address", length = 200)
    String address;

    @Size(max = 200)
    @Column(name = "email", length = 200)
    String email;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    String name;

    @Size(max = 50)
    @Column(name = "phone", length = 50)
    String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_id")
    Category cate;

}