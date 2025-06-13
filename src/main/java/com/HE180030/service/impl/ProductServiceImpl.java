package com.HE180030.service.impl;

import com.HE180030.dto.request.AddProductRequest;
import com.HE180030.dto.request.ProductRequest;
import com.HE180030.dto.response.ProductDetailResponse;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.model.Account;
import com.HE180030.model.Category;
import com.HE180030.model.Product;
import com.HE180030.repository.AccountRepository;
import com.HE180030.repository.CategoryRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    ProductRepository repo;
    CategoryRepository cRepo;
    AccountRepository aRepo;
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
    public List<ProductResponse> getByCategoryID(String id) {
        return repo.findByCategoryId(UrlIdEncoder.decodeId(id))
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
    public void deleteProductByID(String id) {
        logger.info("deleteProductByID");
        Product p = repo.findById(UrlIdEncoder.decodeId(id)).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        p.setCate(null);
        p.setSell(null);
        repo.delete(p);
    }

    @Override
    public ProductDetailResponse getProductDetailsResponseByID(String id) {
        Product p = repo.findById(UrlIdEncoder.decodeId(id)).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return ProductDetailResponse.builder()
                .id(UrlIdEncoder.encodeId(String.valueOf(p.getId())))
                .name(p.getName())
                .image(p.getImage())
                .image2(p.getImage2())
                .image3(p.getImage3())
                .image4(p.getImage4())
                .title(p.getTitle())
                .model(p.getModel())
                .color(p.getColor())
                .description(p.getDescription())
                .delivery(p.getDelivery())
                .categoryId(UrlIdEncoder.encodeId(String.valueOf(p.getCate().getId())))
                .build();
    }

    @Override
    public void insert(AddProductRequest productDTO, int accountId) {
        logger.info("insert");
        Category c = cRepo.findById(productDTO.category()).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        Account a = aRepo.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Account not found"));
        Product p = Product.builder()
                .name(productDTO.name())
                .image(productDTO.image())
                .image2(productDTO.image2())
                .image3(productDTO.image3())
                .image4(productDTO.image4())
                .model(productDTO.model())
                .color(productDTO.color())
                .delivery(productDTO.delivery())
                .price(productDTO.price())
                .title(productDTO.title())
                .description(productDTO.description())
                .cate(c)
                .sell(a)
                .build();
        repo.save(p);
    }

    @Override
    public void deleteProductBySellID(int id) {
        logger.info("deleteProductBySellID");
        List<Product> products = repo.findBySellId(id);
        repo.deleteAll(products);
    }

    @Override
    public ProductResponse getProductResponseById(String id) {
        logger.info("getById");
        Product p = repo.findById(45).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return ProductResponse.builder()
                .id(UrlIdEncoder.encodeId(String.valueOf(p.getId())))
                .name(p.getName())
                .image(p.getImage())
                .title(p.getTitle())
                .color(p.getColor())
                .price(p.getPrice())
                .build();
    }

    @Override
    @Transactional
    public void editProduct(ProductRequest request, int accountId) {
        Product p = repo.findById(UrlIdEncoder.decodeId(request.getId())).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        logger.info(p.toString());
        Category c = cRepo.findById(request.getCategory()).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        Account a = aRepo.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Account not found"));        p.setName(request.getName());
        p.setImage(request.getImage());
        p.setImage2(request.getImage2());
        p.setImage3(request.getImage3());
        p.setImage4(request.getImage4());
        p.setModel(request.getModel());
        p.setColor(request.getColor());
        p.setDelivery(request.getDelivery());
        p.setPrice(request.getPrice());
        p.setTitle(request.getTitle());
        p.setDescription(request.getDescription());
        p.setCate(c);
        p.setSell(a);
        repo.save(p);
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
