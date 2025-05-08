package com.HE180030.service.impl;

import com.HE180030.dto.CategoryDTO;
import com.HE180030.model.Category;
import com.HE180030.repository.CategoryRepository;
import com.HE180030.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {
    private CategoryService categoryService;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllCategoryDTOs() {
        List<Category> mockList = Arrays.asList(
                new Category(1, "cate1"),
                new Category(2, "cate2")
        );
        when(categoryRepository.getAll()).thenReturn(mockList);
        for (CategoryDTO category : categoryService.getAllCategoryDTOs()) {
            System.out.println(category);
        }

    }

    @Test
    void getCategoryDTOByName() {
    }

    @Test
    void insertCategoryDTO() {
    }
}