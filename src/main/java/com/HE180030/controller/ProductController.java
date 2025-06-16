package com.HE180030.controller;

import com.HE180030.dto.request.AddProductRequest;
import com.HE180030.dto.request.DeleteRequest;
import com.HE180030.dto.request.ProductRequest;
import com.HE180030.dto.response.ResponseWrapper;
import com.HE180030.dto.response.CategoryResponse;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.security.utils.SecurityUtils;
import com.HE180030.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/p")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductController {
    ProductService pSrv;
    CategoryService cateSrv;
    QuantitiesSoldService qSrv;
    AccountService aSrv;
    Logger logger = Logger.getLogger(this.getClass().getName());

    /*Tested*/
    @GetMapping("/")
    public ResponseEntity<?> displayAllPaginatedProducts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            PagedResourcesAssembler<ProductResponse> assembler,
            HttpServletRequest request) {
        logger.info("displayAllProducts");
        if (isForbiddenContent(request, "ExportPost.js")) {
            throw new AccessDeniedException("Access denied");
        }

        Page<ProductResponse> productResponsePage = pSrv.getAllPaginatedProduct(page - 1, 9);
        return renderData(assembler, productResponsePage);
    }

    /*Tested*/
    @GetMapping("/last")
    public ResponseEntity<?> getLastProduct() {
        logger.info("getLastProduct");
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(pSrv.getLastProduct())
                        .build()
        );
    }

    /*Tested*/
    @GetMapping("/list")
    public ResponseEntity<?> displayAllProducts(
            @RequestParam(defaultValue = "1") int page) {
        logger.info("displayAllProducts");
        Page<ProductResponse> productResponsePage = pSrv.getAllPaginatedProduct(page - 1, 9);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(productResponsePage)
                        .dataSize(productResponsePage.getSize())
                        .build()
        );
    }

    /*Tested*/
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam("key") String text) {
        List<ProductResponse> list = pSrv.searchProductsByName(text);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(list)
                        .dataSize(list.size())
                        .build()
        );
    }

    /*Tested*/
    @GetMapping("/filter")
    public ResponseEntity<?> filterByCategoryId(
            @RequestParam("id") String categoryId) {
        logger.info("Filter category by ID");
        List<ProductResponse> list = pSrv.getByCategoryID(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
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

    /*Tested*/
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(
            @RequestBody DeleteRequest request) {
        logger.info("delete");
        switch (request.code()) {
            case 100 -> {
                logger.info("Delete product by id");
                qSrv.deleteQuantitiesSoldDTOByProductID(request.id());
                pSrv.deleteProductByID(request.id());
            }
            case 102 -> {
                logger.info("Delete product by sell id");
                int id = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
                pSrv.deleteProductBySellID(id);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .message("Product are successfully deleted")
                        .build()
        );
    }

    /*Tested*/
    @GetMapping("/load")
    public ResponseEntity<?> loadProduct(
            @RequestParam("id") String request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(pSrv.getProductDetailsResponseByID(request))
                        .build()
        );
    }

    /*Tested*/
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
        logger.info(String.valueOf(category));
        int id = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        pSrv.insert(new AddProductRequest(
                request.getName(),
                request.getImage(),
                request.getPrice(),
                request.getTitle(),
                request.getDescription(),
                category,
                0,
                request.getModel(),
                request.getColor(),
                request.getDelivery(),
                request.getImage2(),
                request.getImage3(),
                request.getImage4()), id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .message("Product Added!")
                        .build()
        );
    }

    /*Tested*/
    @GetMapping("/detail")
    public ResponseEntity<?> getProduct(
            @RequestParam("id") String request) {
        logger.info("getProduct");
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(pSrv.getProductResponseById(request))
                        .build()
        );
    }

    /*Tested*/
    @PutMapping("/edit")
    public ResponseEntity<?> editProduct(
            @RequestBody ProductRequest request) {
        logger.info("editProduct");
        List<CategoryResponse> list = cateSrv.getAllCategories();
        if (!request.getCustomCategory().isEmpty() &&
                cateSrv.getCategoryDTOByName(request.getCustomCategory().toUpperCase()) == null) {
            cateSrv.insertCategoryDTO(list.size() + 1, request.getCustomCategory().toUpperCase());
            request.setCategory(list.size() + 1);
        }
        int id = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        pSrv.editProduct(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .message("Product edited successfully")
                        .build()
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
        return ResponseEntity.ok(ResponseWrapper.builder()
                .data(assembler.toModel(postDTOs))
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build()
        );
    }
}
