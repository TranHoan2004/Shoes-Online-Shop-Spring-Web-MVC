package com.HE180030.service.impl;

import com.HE180030.dto.ProductDTO;
import com.HE180030.repository.ProductRepository;
import com.HE180030.repository.impl.ProductRepositoryImpl;
import com.HE180030.service.ProductService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

class ProductServiceImplTest {
//    private ProductService service;
//
//    public ProductServiceImplTest() {
//        ProductRepository repo = new ProductRepositoryImpl();
//        service = new ProductServiceImpl(repo);
//    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void deleteProductDTOByAccountID() {
    }

    @Test
    void getLastProduct() {
    }

    @Test
    void getAllProductDTOs() {
    }

    @Test
    void getAllProductDTOsByCategoryID() {
    }

    @Test
    void searchProductDTOsByName() {
    }

    @Test
    void getProductDTOByID() {
    }

    @Test
    void getListProductDTOsByPage() {
//        List<ProductDTO> list = service.getListProductDTOsByPage(1, 5);
//        System.out.println(list.size());
//        Assertions.assertEquals(5, list.size());
    }

    @Test
    void getAllProductDTOsByAccountID() {
    }

    @Test
    void deleteProductDTOByID() {
    }

    @Test
    void update() {
    }

    @Test
    void insert() {
    }
}