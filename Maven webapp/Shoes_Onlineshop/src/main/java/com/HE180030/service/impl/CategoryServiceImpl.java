package com.HE180030.service.impl;

import com.HE180030.dto.CategoryDTO;
import com.HE180030.repository.CartRepository;
import com.HE180030.repository.CategoryRepository;
import com.HE180030.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAll() {
        return null;
    }

    @Override
    public CategoryDTO getCategoryDTOByName(String name) {
        return null;
    }

    @Override
    public void insertCategoryDTO(int categoryId, String name) {

    }
}
