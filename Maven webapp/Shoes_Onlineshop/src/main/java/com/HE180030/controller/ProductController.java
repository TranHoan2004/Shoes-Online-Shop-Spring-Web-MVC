package com.HE180030.controller;

import com.HE180030.dto.AccountDTO;
import com.HE180030.dto.CategoryDTO;
import com.HE180030.dto.ProductDTO;
import com.HE180030.dto.SearchForm;
import com.HE180030.service.CategoryService;
import com.HE180030.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
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

    @GetMapping("/manager_product")
    public String manageProduct(Model model, HttpSession session, @RequestParam("page") String page) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        int accountID = accountDTO.getId();
        //
        List<CategoryDTO> list = categoryService.getAll();
        model.addAttribute("listCategory", list);
        //
        List<ProductDTO> products = productService.getAllProductDTOsByAccountID(accountID);
        displayProductByPage(model, products, page);
        return "manager-product";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("pid") int id, Model model) {
        try {
            //
            ProductDTO productDetail = productService.getProductDTOByID(id);
            System.out.println(productDetail == null ? "Null" : "not null");
            model.addAttribute("detail", productDetail);

            //
            List<CategoryDTO> list = categoryService.getAll();
            model.addAttribute("listCategory", list);

            //
            ProductDTO product = productService.getLastProduct();
            model.addAttribute("lastProduct", product);

            // for head container
            model.addAttribute("searchForm", new SearchForm());
        } catch (Exception e) {
            Logger.getLogger(ProductController.class.getName()).log(Level.ALL, e.getMessage(), e);
        }
        return "detail";
    }

    private void displayProductByPage(Model model, @NotNull List<ProductDTO> list, @NotNull String pageRequest) {
        int page;
        int size = list.size();
        int numberpage = 6;
        int num = (size % numberpage == 0 ? (size / numberpage) : ((size / numberpage) + 1));
        if (pageRequest.isEmpty()) {
            page = 1;
        } else {
            page = Integer.parseInt(pageRequest);
        }
        int start = (page - 1) * numberpage;
        int end = Math.min(page * numberpage, size);
        List<ProductDTO> listByPage = productService.getListProductDTOsByPage(list, start, end);
        model.addAttribute("listProducts", listByPage);
        model.addAttribute("num", num);
        model.addAttribute("page", page);
    }
}
