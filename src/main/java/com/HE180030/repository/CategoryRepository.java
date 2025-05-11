package com.HE180030.repository;

import com.HE180030.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
//    List<Category> getAll();
//
//    Category getByName(String name);
//
//    void insert(int categoryId, String name);
//
//    String getById(int categoryId);
}
