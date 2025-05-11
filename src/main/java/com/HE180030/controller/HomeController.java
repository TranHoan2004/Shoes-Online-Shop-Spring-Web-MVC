package com.HE180030.controller;

import com.HE180030.dto.response.ApiResponse;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.service.CategoryService;
import com.HE180030.service.ProductService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public ResponseEntity<String> init() {
        logger.info("Home Controller");
        return ResponseEntity.status(HttpStatus.CONTINUE).body("Ready to init");
    }
}
