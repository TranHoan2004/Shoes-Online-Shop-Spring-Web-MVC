package com.HE180030.service;

import com.HE180030.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();

    CategoryDTO getCategoryDTOByName(String name);

    void insertCategoryDTO(long categoryId, String name);
}
