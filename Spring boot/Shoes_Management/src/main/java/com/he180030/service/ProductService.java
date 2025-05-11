package com.HE180030.service;

import com.HE180030.dto.request.AddProductRequest;
import com.HE180030.dto.request.ProductRequest;
import com.HE180030.dto.response.ProductDetailResponse;
import com.HE180030.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductResponse> getAllPaginatedProductDTO(int page, int size);

    ProductResponse getLastProduct();

    List<ProductResponse> getByCategoryId(int id);

    List<ProductResponse> searchProductDTOsByName(String text);

    List<ProductResponse> getAllProducts();

    void deleteProductDTOByID(int id);

    ProductDetailResponse getProductDTOByID(int id);

    void insert(AddProductRequest productDTO);

    void deleteProductBySellID(int id);

    void delete(int id);

    ProductResponse getById(int id);

    void editProduct(ProductRequest request);
}
