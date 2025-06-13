package com.HE180030.service.impl;

import com.HE180030.dto.response.CategoryResponse;
import com.HE180030.model.Category;
import com.HE180030.repository.CategoryRepository;
import com.HE180030.service.CategoryService;
import com.HE180030.utils.UrlIdEncoder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository repo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public List<CategoryResponse> getAllCategories() {
        logger.info("getAllCategories");
        return repo.findAll().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryDTOByName(String name) {
        logger.info("getCategoryDTOByName");
        Category c = repo.findByName(name);
        return c == null ? null : CategoryResponse.builder()
                .name(c.getName())
                .id(UrlIdEncoder.encodeId(String.valueOf(c.getId())))
                .build();
    }

    @Override
    public void insertCategoryDTO(int categoryId, String name) {
        logger.info("insertCategoryDTO");
        Category c = Category.builder()
                .id(categoryId)
                .name(name)
                .build();
        repo.save(c);
    }

    private CategoryResponse convert(@NotNull Category category) {
        return CategoryResponse.builder()
                .id(UrlIdEncoder.encodeId(String.valueOf(category.getId())))
                .name(category.getName())
                .build();
    }
}
