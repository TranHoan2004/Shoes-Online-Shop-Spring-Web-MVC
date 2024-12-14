package com.HE180030.controller;

import com.HE180030.dto.ProductDTO;
import com.HE180030.service.CategoryService;
import com.HE180030.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model, @ModelAttribute("page") String pageRequest) {
        // main content
        List<ProductDTO> list = productService.getAllProductDTOs();
        int page;
        int size = list.size();
        int numberpage = 6;

        // number of page
        int num = (size % numberpage == 0 ? (size / numberpage) : ((size / numberpage) + 1));
        if (pageRequest.isEmpty()) {
            page = 1;
        } else {
            page = Integer.parseInt(pageRequest);
        }
        int start;
        int end;
        start = (page - 1) * numberpage;
        end = Math.min(page * numberpage, size);
        model.addAttribute("products", productService.getListProductDTOsByPage(start, end));
        model.addAttribute("num", num);
        model.addAttribute("page", page);
        model.addAttribute("last", productService.getLastProduct());
        model.addAttribute("listCategory", categoryService.getAll());
        return "home";
    }

    @GetMapping("/home")
    public void home() {
    }

}
