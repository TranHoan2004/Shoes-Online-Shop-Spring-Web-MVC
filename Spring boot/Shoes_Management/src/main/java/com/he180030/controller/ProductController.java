package com.HE180030.controller;

import com.HE180030.dto.request.AddProductRequest;
import com.HE180030.dto.request.DeleteRequest;
import com.HE180030.dto.request.ProductRequest;
import com.HE180030.dto.response.ApiResponse;
import com.HE180030.dto.response.CategoryResponse;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/product")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService pSrv;
    CategoryService cateSrv;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public ProductController(ProductService pSrv, CategoryService cateSrv) {
        this.pSrv = pSrv;
        this.cateSrv = cateSrv;
    }

    /*Tested*/
    @GetMapping("")
    public ResponseEntity<?> displayAllPaginatedProducts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            PagedResourcesAssembler<ProductResponse> assembler,
            HttpServletRequest request) {
        logger.info("displayAllProducts");
        if (isForbiddenContent(request, "ExportPost.js")) {
            throw new AccessDeniedException("Access denied");
        }

        Page<ProductResponse> productResponsePage = pSrv.getAllPaginatedProductDTO(page - 1, 9);
        return renderData(assembler, productResponsePage);
    }

    /*Tested*/
    @GetMapping("/last")
    public ResponseEntity<?> getLastProduct() {
        logger.info("getLastProduct");
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(pSrv.getLastProduct())
                        .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<?> displayAllProducts(
            @RequestParam(defaultValue = "1") int page) {
        logger.info("displayAllProducts");
        Page<ProductResponse> productResponsePage = pSrv.getAllPaginatedProductDTO(page - 1, 9);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(productResponsePage)
                        .dataSize(productResponsePage.getSize())
        );
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam("key") String text) {
        List<ProductResponse> list = pSrv.searchProductDTOsByName(text);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(list)
                        .dataSize(list.size())
                        .build()
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterByCategoryId(
            @RequestParam("id") int categoryId) {
        logger.info("Filter category by ID");
        List<ProductResponse> list = pSrv.getByCategoryId(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(Map.of(
                                "posts list", list,
                                "category id", categoryId
                        ))
                        .dataSize(list.size())
                        .build()
        );
    }

    // khong can /managerProduct
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(
            @RequestBody DeleteRequest request) {
        logger.info("delete");
        switch (request.getCode()) {
            case 100 -> {
                logger.info("Delete product by id");
                pSrv.deleteProductDTOByID(request.getId());
            }
            case 101 -> {
                logger.info("Delete product");
                pSrv.delete(request.getId());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Product are successfully deleted")
        );
    }

    @GetMapping("/load")
    public ResponseEntity<?> loadProduct(
            @RequestParam int id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(pSrv.getProductDTOByID(id))
        );
    }

    @GetMapping("/add")
    public ResponseEntity<?> addProduct(
            @RequestBody ProductRequest request) {
        logger.info("addProduct");
        List<CategoryResponse> categories = cateSrv.getAllCategories();
        String customCategory = request.getCustomCategory();
        int category = 0;
        if (customCategory != null && cateSrv.getCategoryDTOByName(customCategory) == null) {
            cateSrv.insertCategoryDTO(categories.size() + 1, customCategory.toUpperCase());
            category = categories.size() + 1;
        }
        pSrv.insert(AddProductRequest.builder()
                .name(request.getName())
                .image(request.getImage())
                .price(request.getPrice())
                .title(request.getTitle())
                .description(request.getDescription())
                .category(category)
                .model(request.getModel())
                .color(request.getColor())
                .delivery(request.getDelivery())
                .image2(request.getImage2())
                .image3(request.getImage3())
                .image4(request.getImage4())
                .build());
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Product Added!")
        );
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getProduct(
            @RequestBody int id) {
        logger.info("getProduct");
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(pSrv.getById(id))
        );
    }

    private boolean isForbiddenContent(
            @NotNull HttpServletRequest request,
            String key) {
        String requestedBy = request.getHeader("X-Requested-By");
        return requestedBy == null || !requestedBy.equals(key);
    }

    @NotNull
    private ResponseEntity<?> renderData(
            @NotNull PagedResourcesAssembler<ProductResponse> assembler,
            Page<ProductResponse> postDTOs) {
        return ResponseEntity.ok(ApiResponse.builder()
                .data(assembler.toModel(postDTOs))
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build()
        );
    }
}
