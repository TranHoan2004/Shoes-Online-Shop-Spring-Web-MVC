package com.HE180030.repository;

import com.HE180030.model.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> getAll();

    Category getByName(String name);

    void insert(int categoryId, String name);

    String getById(int categoryId);
}
