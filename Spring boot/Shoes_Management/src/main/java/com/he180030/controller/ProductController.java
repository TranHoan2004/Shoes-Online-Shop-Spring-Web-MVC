package com.HE180030.controller;

import com.HE180030.dto.*;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.service.*;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final ReviewService reviewService;
    private final QuantitiesSoldService quantitiesSoldService;

    public ProductController(ProductService productService, CategoryService categoryService, CartService cartService, ReviewService reviewService, QuantitiesSoldService quantitiesSoldService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.reviewService = reviewService;
        this.quantitiesSoldService = quantitiesSoldService;
    }

    @GetMapping("/managerProduct")
    public String manageProduct(Model model, HttpSession session, @RequestParam("page") String page) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        int accountID = accountDTO.getId();
        //
        List<CategoryDTO> list = categoryService.getAllCategoryDTOs();
        model.addAttribute("listCategory", list);
        //
        model.addAttribute("product", new ProductDTO());
        //
        List<ProductDTO> products = productService.getAllProductDTOsByAccountID(accountID);
        displayProductByPage(model, products, page);
        return "manager-product";
    }

    @GetMapping("/detail")
    public String redirectToDetailScreen(@RequestParam("pid") int id, Model model) {
        try {
            //
            ProductDTO productDetail = productService.getProductDTOByID(id);
            System.out.println(productDetail == null ? "Null" : "not null");
            model.addAttribute("detail", productDetail);

            //
            List<CategoryDTO> list = categoryService.getAllCategoryDTOs();
            model.addAttribute("listCategory", list);

            //
            ProductDTO product = productService.getLastProduct();
            model.addAttribute("lastProduct", product);

            // for head container
            model.addAttribute("searchForm", new SearchForm());

            //
            model.addAttribute("cart", new CartDTO());
        } catch (Exception e) {
            Logger.getLogger(ProductController.class.getName()).log(Level.ALL, e.getMessage(), e);
        }
        return "detail";
    }

    @GetMapping("/delete")
    public String deleteProduct(@ModelAttribute("id") int id) {
        cartService.deleteCartDTOByProductID(id);
        reviewService.deleteReviewDTOByProductID(id);
        quantitiesSoldService.deleteQuantitiesSoldDTOByProductID(id);
        productService.deleteProductDTOByID(id);
        return "redirect:/managerProduct";
    }

    @GetMapping("/loadProduct")
    public String loadProduct(@ModelAttribute("id") int id, Model model) {
        List<CategoryDTO> listCategories = categoryService.getAllCategoryDTOs();
        List<ProductDTO> listProducts = productService.getAllProductDTOs();
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("detail", productService.getProductDTOByID(id));
        model.addAttribute("listCategories", listCategories);
        return "edit-product";
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

    // new code
    @GetMapping("/product/list")
    public ResponseEntity<?> displayAllProducts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            PagedResourcesAssembler<ProductResponse> assembler) {
        return null;
    }
}
