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

    List<ProductResponse> getByCategoryID(String id);

    ProductResponse getProductResponseById(String id);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> searchProductsByName(String text);

    ProductDetailResponse getProductDetailsResponseByID(String id);

    void insert(AddProductRequest productDTO, int accountId);

    void deleteProductBySellID(int id);

    void deleteProductByID(String id);

    void editProduct(ProductRequest request, int accountId);
}
