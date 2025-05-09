package com.HE180030.service;

import com.HE180030.dto.response.ProductResponse;
import com.HE180030.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    // new code
    Page<ProductResponse> getAllProductDTO(int page, int size);

    ProductResponse getLastProduct();

    List<Product> getById(int id);
}
