package com.HE180030.service;

import com.HE180030.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryDTOByName(String name);

    void insertCategoryDTO(int categoryId, String name);

    String getNameByID(int id);
}
