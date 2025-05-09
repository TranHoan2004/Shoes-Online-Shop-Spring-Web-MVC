package com.HE180030.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(length = 50)
    String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    Set<Product> products;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    Set<Supplier> suppliers;
}
