package com.HE180030.service;

import com.HE180030.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategoryDTOs();

    CategoryDTO getCategoryDTOByName(String name);

    void insertCategoryDTO(int categoryId, String name);
    String getNameByID(int id);
}
