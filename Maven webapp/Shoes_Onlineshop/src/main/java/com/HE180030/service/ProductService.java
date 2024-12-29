package com.HE180030.service;

import com.HE180030.dto.ProductDTO;
import com.HE180030.model.Product;

import java.util.List;

public interface ProductService {
    ProductDTO getLastProduct() throws Exception;

    List<ProductDTO> getAllProductDTOs();

    List<ProductDTO> getAllProductDTOsByCategoryID(int categoryId);

    List<ProductDTO> searchProductDTOsByName(String txt);

    ProductDTO getProductDTOByID(int id);

    List<ProductDTO> getListProductDTOsByPage(int start, int end);

    List<ProductDTO> getAllProductDTOsByAccountID(int sellID);

    void deleteProductDTOByAccountID(int id);

    void deleteProductDTOByID(int id);

    void update(ProductDTO productDTO) throws Exception;

    void insert(ProductDTO productDTO);
}
