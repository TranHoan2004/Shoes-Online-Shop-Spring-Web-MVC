package com.HE180030.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @Column(name = "cid")
    private long id;

    @Column(name = "cname", length = 50)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @OneToMany(mappedBy = "category")
    private List<Supplier> suppliers;
}
