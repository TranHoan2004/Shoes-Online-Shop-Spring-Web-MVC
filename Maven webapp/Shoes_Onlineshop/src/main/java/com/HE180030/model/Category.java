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
@Table(name = "Category")
public class Category {
    @Id
    @Column(name = "cid")
    private int id;

    @Column(name = "cname", length = 50)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @OneToMany(mappedBy = "category")
    private List<Supplier> suppliers;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
