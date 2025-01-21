package com.HE180030.controller;

import com.HE180030.dto.ProductDTO;
import com.HE180030.dto.SearchForm;
import com.HE180030.service.AccountService;
import com.HE180030.service.CategoryService;
import com.HE180030.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final AccountService accountService;

    public HomeController(ProductService productService, CategoryService categoryService, AccountService accountService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model, @ModelAttribute("page") String pageRequest, SessionStatus sessionStatus) {
        try {
            List<ProductDTO> list = productService.getAllProductDTOs();

            // for products list container
            displayProductByPage(model, list, pageRequest);

            // for left container
            getCategoryAndLastProduct(model);

            // for head container
            model.addAttribute("searchForm", new SearchForm());
            if (session.getAttribute("account") == null) {
                session.setAttribute("account", null);
            }
        } catch (Exception e) {
            Logger.getLogger(HomeController.class.getName()).log(Level.ALL, e.getMessage(), e);
        }
        return "home";
    }

    @GetMapping("/home")
    public String pagination(@RequestParam("page") String page, Model model) {
        try {
            // for products list container
            displayProductByPage(model, productService.getAllProductDTOs(), page);

            // for head container
            model.addAttribute("searchForm", new SearchForm());

            // for left container
            getCategoryAndLastProduct(model);
        } catch (Exception e) {
            Logger.getLogger(HomeController.class.getName()).log(Level.ALL, e.getMessage(), e);
        }
        return "home";
    }

    @GetMapping("/category")
    public String filterByCategory(Model model, @RequestParam("id") int categoryID, @ModelAttribute("page") String pageRequest) {
        try {
            // for products list container
            List<ProductDTO> list = productService.getAllProductDTOsByCategoryID(categoryID);
            displayProductByPage(model, list, pageRequest);
            // for head container
            model.addAttribute("searchForm", new SearchForm());

            // for left container
            getCategoryAndLastProduct(model);

            // category id
            model.addAttribute("cateID", categoryID);
        } catch (Exception e) {
            Logger.getLogger(HomeController.class.getName()).log(Level.ALL, e.getMessage(), e);
        }
        return "home";
    }

    @PostMapping("/search")
    public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {
        try {
            List<ProductDTO> products = productService.searchProductDTOsByName(searchForm.getText());
            getCategoryAndLastProduct(model);
            model.addAttribute("list", products);
            model.addAttribute("searchForm", searchForm);
        } catch (Exception e) {
            Logger.getLogger(HomeController.class.getName()).log(Level.ALL, e.getMessage(), e);
        }
        return "home";
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
        model.addAttribute("list", listByPage);
        model.addAttribute("num", num);
        model.addAttribute("page", page);
    }

    private void getCategoryAndLastProduct(@NotNull Model model) throws Exception {
        model.addAttribute("listCategory", categoryService.getAll());
        model.addAttribute("last", productService.getLastProduct());
    }

}
