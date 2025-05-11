package com.HE180030.service;

import com.HE180030.dto.ProductDTO;
import com.HE180030.dto.response.ProductDetailResponse;
import com.HE180030.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductResponse> getAllPaginatedProductDTO(int page, int size);

    ProductResponse getLastProduct();

    List<ProductResponse> getById(int id);

    List<ProductResponse> searchProductDTOsByName(String text);

    List<ProductResponse> getAllProducts();

    void deleteProductDTOByID(int id);

    ProductDetailResponse getProductDTOByID(int id);
}
