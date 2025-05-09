package com.HE180030.service.impl;

import com.HE180030.dto.response.CategoryResponse;
import com.HE180030.model.Category;
import com.HE180030.repository.CategoryRepository;
import com.HE180030.service.CategoryService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repo;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.repo = categoryRepository;
    }

    private CategoryResponse convert(@NotNull Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return repo.findAll().stream().map(this::convert).collect(Collectors.toList());
    }
}
