package com.HE180030.repository;

import com.HE180030.model.*;

import java.util.List;

public interface CategoryRepository {
    List<Category> getAllCategory();
    Category getByName(String name);
    void insertCategory(long categoryId, String name);
//    Category getById(long id);
}
