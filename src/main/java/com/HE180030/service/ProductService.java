package com.HE180030.service;

import com.HE180030.dto.request.AddProductRequest;
import com.HE180030.dto.request.ProductRequest;
import com.HE180030.dto.response.ProductDetailResponse;
import com.HE180030.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductResponse> getAllPaginatedProduct(int page, int size);

    ProductResponse getLastProduct();

    List<ProductResponse> getByCategoryID(int id);

    List<ProductResponse> searchProductsByName(String text);

    List<ProductResponse> getAllProducts();

    void deleteProductByID(int id);

    ProductDetailResponse getProductByID(int id);

    void insert(AddProductRequest productDTO);

    void deleteProductBySellID(int id);

    void delete(int id);

    ProductResponse getById(int id);

    void editProduct(ProductRequest request);
}
