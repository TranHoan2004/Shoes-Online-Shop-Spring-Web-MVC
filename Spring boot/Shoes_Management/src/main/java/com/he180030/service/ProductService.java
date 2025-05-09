package com.HE180030.service;

import com.HE180030.dto.ProductDTO;
import com.HE180030.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    // new code
    Page<ProductResponse> getAllProductDTO(int page, int size);
}
