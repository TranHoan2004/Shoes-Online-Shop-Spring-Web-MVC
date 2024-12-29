package com.HE180030.service.impl;

import com.HE180030.dto.ProductDTO;
import com.HE180030.model.Product;
import com.HE180030.repository.ProductRepository;
import com.HE180030.service.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "productService")
@Transactional(propagation = Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void deleteProductDTOByAccountID(int id) {

    }

    @Override
    public ProductDTO getLastProduct() throws Exception {
        Product product = productRepository.getLastProduct();
        if (product != null) {
            return ProductDTO.builder()
                    .delivery(product.getDelivery())
                    .name(product.getName())
                    .model(product.getModel())
                    .price(product.getPrice())
                    .color(product.getColor())
                    .title(product.getTitle())
                    .description(product.getDescription())
                    .image(product.getImage())
                    .image2(product.getImage2())
                    .image3(product.getImage3())
                    .image4(product.getImage4())
                    .build();
        }
        throw new Exception("No result");
    }

    @Override
    public List<ProductDTO> getAllProductDTOs() {
        List<ProductDTO> list = new ArrayList<>();
        for (Product product : productRepository.getAll()) {
            list.add(convert(product));
        }
        return list;
    }

    @Override
    public List<ProductDTO> getAllProductDTOsByCategoryID(int categoryId) {
        List<Product> list = productRepository.getAllByCategoryID(categoryId);
        List<ProductDTO> result = new ArrayList<>();
        for (Product product : list) {
            result.add(convert(product));
        }
        return result;
    }

    @Override
    public List<ProductDTO> searchProductDTOsByName(String txt) {
        return null;
    }

    @Override
    public ProductDTO getProductDTOByID(int id) {
        return null;
    }

    @Override
    public List<ProductDTO> getListProductDTOsByPage(int start, int end) {
        List<Product> products = productRepository.getAll();
        products = productRepository.getListByPage(products, start, end);
        return products.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProductDTOsByAccountID(int sellID) {
        return null;
    }

    @Override
    public void deleteProductDTOByID(int id) {

    }

    @Override
    public void update(ProductDTO productDTO) throws Exception {

    }

    @Override
    public void insert(ProductDTO productDTO) {

    }

    private ProductDTO convert(@NotNull Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .image(product.getImage())
                .image2(product.getImage2())
                .image3(product.getImage3())
                .image4(product.getImage4())
                .price(product.getPrice())
                .title(product.getTitle())
                .description(product.getDescription())
                .model(product.getModel())
                .color(product.getColor())
                .delivery(product.getDelivery())
                .build();
    }
}
