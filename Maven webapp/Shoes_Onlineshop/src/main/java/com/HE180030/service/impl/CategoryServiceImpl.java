package com.HE180030.service.impl;

import com.HE180030.dto.CategoryDTO;
import com.HE180030.model.Category;
import com.HE180030.repository.CategoryRepository;
import com.HE180030.service.CategoryService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<CategoryDTO> list = new ArrayList<>();
        for (Category c : categoryRepository.getAll()) {
            list.add(convert(c));
        }
        return list;
    }

    @Override
    public CategoryDTO getCategoryDTOByName(String name) {
        return null;
    }

    @Override
    public void insertCategoryDTO(int categoryId, String name) {

    }

    private CategoryDTO convert(@NotNull Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
