package com.HE180030.service;

import com.HE180030.dto.ProductDTO;
import com.HE180030.model.Product;

import java.util.List;

public interface ProductService {
    ProductDTO getLastProduct();

    List<ProductDTO> getAllProductDTOs();

    List<ProductDTO> getAllProductDTOsByCategoryID(long categoryId);

    List<ProductDTO> searchProductDTOsByName(String txt);

    ProductDTO getProductDTOByID(long id);

    List<ProductDTO> getListProductDTOsByPage(List<Product> list, int start, int end);

    List<ProductDTO> getAllProductDTOsByAccountID(long sellID);

    void deleteProductDTOByAccountID(long id);

    void deleteProductDTOByID(long id);

    void update(ProductDTO productDTO) throws Exception;

    void insert(ProductDTO productDTO);
}
