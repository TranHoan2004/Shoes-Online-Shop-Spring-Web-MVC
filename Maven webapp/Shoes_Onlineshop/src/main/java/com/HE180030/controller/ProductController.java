package com.HE180030.controller;

import com.HE180030.dto.CategoryDTO;
import com.HE180030.dto.ProductDTO;
import com.HE180030.service.CategoryService;
import com.HE180030.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/manage_product")
    public String manageProduct(Model model, HttpSession session) {
        return "manage-product";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("pid") int id, Model model) {
        try {
            //
            ProductDTO productDetail = productService.getProductDTOByID(id);
            model.addAttribute("detail", productDetail);

            //
            List<CategoryDTO> list = categoryService.getAll();
            model.addAttribute("listCategory", list);

            //
            ProductDTO product = productService.getLastProduct();
            model.addAttribute("lastProduct", product);
        } catch (Exception e) {
            Logger.getLogger(ProductController.class.getName()).log(Level.ALL, e.getMessage(), e);
        }
        return "detail";
    }
}
