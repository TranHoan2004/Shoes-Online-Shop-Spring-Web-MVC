package com.HE180030.controller;

import com.HE180030.dto.response.ApiResponse;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.model.Product;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/product")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService pSrv;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public ProductController(ProductService pSrv) {
        this.pSrv = pSrv;
    }

    @GetMapping("")
    public ResponseEntity<?> displayAllProducts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            PagedResourcesAssembler<ProductResponse> assembler,
            HttpServletRequest request) {
        logger.info("displayAllProducts");
//        if (isForbiddenContent(request, "ExportPost.js")) {
//            throw new AccessDeniedException("Access denied");
//        }

        Page<ProductResponse> productResponsePage = pSrv.getAllProductDTO(page - 1, 9);
        return renderData(assembler, productResponsePage);
    }

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
