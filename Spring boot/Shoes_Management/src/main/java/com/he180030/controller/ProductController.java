package com.HE180030.controller;

import com.HE180030.dto.AccountDTO;
import com.HE180030.dto.CategoryDTO;
import com.HE180030.dto.ProductDTO;
import com.HE180030.dto.response.ApiResponse;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
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
    CartService cSrv;
    ReviewService rSrv;
    QuantitiesSoldService qSrv;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public ProductController(ProductService pSrv, CategoryService cateSrv, CartService cSrv, ReviewService rSrv, QuantitiesSoldService qSrv) {
        this.pSrv = pSrv;
        this.cateSrv = cateSrv;
        this.cSrv = cSrv;
        this.rSrv = rSrv;
        this.qSrv = qSrv;
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
            @RequestParam(value = "page", defaultValue = "1") int page) {
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
    public ResponseEntity<?> searchProducts(@RequestBody String text) {
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
            @RequestParam int categoryId) {
        logger.info("Filter category by ID");
        List<ProductResponse> list = pSrv.getById(categoryId);
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
    public ResponseEntity<?> deleteProduct(@RequestBody int id) {
        cSrv.deleteCartDTOByProductID(id);
        rSrv.deleteReviewDTOByProductID(id);
        qSrv.deleteQuantitiesSoldDTOByProductID(id);
        pSrv.deleteProductDTOByID(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Cart, review, quantities sold, product successfully deleted")
        );
    }

    @GetMapping("/load")
    public ResponseEntity<?> loadProduct(@ModelAttribute("id") int id, Model model) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(pSrv.getProductDTOByID(id))
        );
    }

    private boolean isForbiddenContent(@NotNull HttpServletRequest request, String key) {
        String requestedBy = request.getHeader("X-Requested-By");
        return requestedBy == null || !requestedBy.equals(key);
    }

    @NotNull
    private ResponseEntity<?> renderData(@NotNull PagedResourcesAssembler<ProductResponse> assembler, Page<ProductResponse> postDTOs) {
        return ResponseEntity.ok(ApiResponse.builder()
                .data(assembler.toModel(postDTOs))
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build()
        );
    }
}
