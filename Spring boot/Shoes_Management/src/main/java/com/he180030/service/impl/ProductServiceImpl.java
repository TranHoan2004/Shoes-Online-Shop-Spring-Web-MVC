package com.HE180030.service.impl;

import com.HE180030.dto.ProductDTO;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.model.Product;
import com.HE180030.repository.ProductRepository;
import com.HE180030.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
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

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
    ProductRepository repo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public void deleteProductDTOByAccountID(int id) {

    }

    @Override
    public ProductDTO getLastProduct() throws Exception {
        Product product = repo.getLastProduct();
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
        for (Product product : repo.getAll()) {
            list.add(convert(product));
        }
        return list;
    }

    @Override
    public List<ProductDTO> getAllProductDTOsByCategoryID(int categoryId) {
        List<Product> list = repo.getAllByCategoryID(categoryId);
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
        Product product = repo.getByID(id);
        return convert(product);
    }

    @Override
    public List<ProductDTO> getListProductDTOsByPage(@NotNull List<ProductDTO> products, int start, int end) {
        List<Product> input = new ArrayList<>();
        List<ProductDTO> list = new ArrayList<>();
        for (ProductDTO product : products) {
            input.add(convert(product));
        }
        input = repo.getListByPage(input, start, end);
        for (Product product : input) {
            list.add(convert(product));
        }
        return list;
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

    @Override
    public Page<ProductResponse> getAllProductDTO(int page, int size) {
        logger.info("getAllProductDTO");
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = repo.findAll(pageable);
        long count = repo.count();
        return getProductResponses(pageable, products, count);
    }

    private ProductDTO convert(@NotNull Product product) {
        return ProductDTO.builder()
                .id(product.getId())
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

    private Product convert(@NotNull ProductDTO product) {
        return Product.builder()
                .id(product.getId())
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

    private ProductResponse convertToResponse(@NotNull Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .image(product.getImage())
                .price(product.getPrice())
                .title(product.getTitle())
                .build();
    }

    @Contract("_, _, _ -> new")
    private @NotNull Page<ProductResponse> getProductResponses(Pageable pageable, @NotNull Page<Product> products, long count) {
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No posts");
        }
        List<Product> postDTOs = products.getContent();
        List<ProductResponse> postDTOList = new ArrayList<>();
        postDTOs.forEach(p -> postDTOList.add(convertToResponse(p)));
        return new PageImpl<>(postDTOList, pageable, count);
    }
}
