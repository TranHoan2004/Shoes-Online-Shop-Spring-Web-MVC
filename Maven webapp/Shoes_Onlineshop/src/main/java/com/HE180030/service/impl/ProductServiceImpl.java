package com.HE180030.service.impl;

import com.HE180030.dto.ProductDTO;
import com.HE180030.model.Product;
import com.HE180030.repository.ProductRepository;
import com.HE180030.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void deleteProductDTOByAccountID(long id) {

    }

    @Override
    public ProductDTO getLastProduct() {
        return null;
    }

    @Override
    public List<ProductDTO> getAllProductDTOs() {
        return null;
    }

    @Override
    public List<ProductDTO> getAllProductDTOsByCategoryID(long categoryId) {
        return null;
    }

    @Override
    public List<ProductDTO> searchProductDTOsByName(String txt) {
        return null;
    }

    @Override
    public ProductDTO getProductDTOByID(long id) {
        return null;
    }

    @Override
    public List<ProductDTO> getListProductDTOsByPage(List<Product> list, int start, int end) {
        return null;
    }

    @Override
    public List<ProductDTO> getAllProductDTOsByAccountID(long sellID) {
        return null;
    }

    @Override
    public void deleteProductDTOByID(long id) {

    }

    @Override
    public void update(ProductDTO productDTO) throws Exception {

    }

    @Override
    public void insert(ProductDTO productDTO) {

    }
}
