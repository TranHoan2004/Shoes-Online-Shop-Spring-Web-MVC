package com.HE180030.service.impl;

import com.HE180030.dto.request.AddProductRequest;
import com.HE180030.dto.request.ProductRequest;
import com.HE180030.dto.response.ProductDetailResponse;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.model.Product;
import com.HE180030.repository.ProductRepository;
import com.HE180030.service.ProductService;
import com.HE180030.utils.UrlIdEncoder;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    ProductRepository repo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Page<ProductResponse> getAllPaginatedProduct(int page, int size) {
        logger.info("getAllProductDTO");
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = repo.findAll(pageable);
        long count = repo.count();
        return getProductResponses(pageable, products, count);
    }

    @Override
    public ProductResponse getLastProduct() {
        return convert(repo.findLastProduct());
    }

    @Override
    public List<ProductResponse> getByCategoryID(int id) {
        return repo.findByCategoryId(id)
                .stream().map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> searchProductsByName(String text) {
        return repo.searchByName("%" + text + "%")
                .stream().map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return repo.findAll()
                .stream().map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductByID(int id) {
        logger.info("deleteProductByID");
    }

    @Override
    public ProductDetailResponse getProductByID(int id) {
        return null;
    }

    @Override
    public void insert(AddProductRequest productDTO) {

    }

    @Override
    public void deleteProductBySellID(int id) {
        logger.info("deleteProductBySellID");
        List<Product> products = repo.findBySellId(id);
        repo.deleteAll(products);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public ProductResponse getById(int id) {
        return null;
    }

    @Override
    public void editProduct(ProductRequest request) {

    }

    private ProductResponse convert(@NotNull Product product) {
        return ProductResponse.builder()
                .id(UrlIdEncoder.encodeId(String.valueOf(product.getId())))
                .name(product.getName())
                .image(product.getImage())
                .price(product.getPrice())
                .title(product.getTitle())
                .color(product.getColor())
                .build();
    }

    @Contract("_, _, _ -> new")
    private @NotNull Page<ProductResponse> getProductResponses(Pageable pageable, @NotNull Page<Product> products, long count) {
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No posts");
        }
        List<Product> postDTOs = products.getContent();
        List<ProductResponse> postDTOList = new ArrayList<>();
        postDTOs.forEach(p -> postDTOList.add(convert(p)));
        return new PageImpl<>(postDTOList, pageable, count);
    }
}
