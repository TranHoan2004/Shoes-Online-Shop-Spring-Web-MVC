package com.HE180030.controller;

import com.HE180030.dto.response.ApiResponse;
import com.HE180030.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryController {
    Logger logger = LoggerFactory.getLogger(CategoryController.class);
    CategoryService cateSrv;

//    public CategoryController(CategoryService categoryService) {
//        this.cateSrv = categoryService;
//    }

    /*Tested*/
    @GetMapping("")
    public ResponseEntity<?> displayCategory() {
        logger.info("Display category");
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Display category")
                        .data(cateSrv.getAllCategories())
                        .build()
        );
    }
}
