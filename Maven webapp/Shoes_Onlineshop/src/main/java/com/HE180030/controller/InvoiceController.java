package com.HE180030.controller;

import com.HE180030.dto.AccountDTO;
import com.HE180030.dto.CartDTO;
import com.HE180030.dto.InvoiceDTO;
import com.HE180030.dto.ProductDTO;
import com.HE180030.service.AccountService;
import com.HE180030.service.CartService;
import com.HE180030.service.InvoiceService;
import com.HE180030.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final AccountService accountService;
    private final ProductService productService;
    private final CartService cartService;

    public InvoiceController(InvoiceService invoiceService, AccountService accountService, ProductService productService, CartService cartService) {
        this.invoiceService = invoiceService;
        this.accountService = accountService;
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping("/invoice")
    public String redirectToInvoice(Model model, HttpSession session) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        int accountID = accountDTO.getId();
        List<InvoiceDTO> invoices = invoiceService.getAllInvoiceByID(accountID);
        List<AccountDTO> accounts = accountService.getAllAccountDTOs();
        model.addAttribute("listAllInvoices", invoices);
        model.addAttribute("listAllAccounts", accounts);
        return "invoice";
    }

    @GetMapping("/order")
    public String redirectToOrderScreen(Model model, HttpSession session) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        List<CartDTO> cartDTOs = cartService.getCartDTOByAccountID(accountDTO.getId());
        List<ProductDTO> productDTOs = productService.getAllProductDTOs();
        AtomicReference<Double> totalMoney = new AtomicReference<>((double) 0);
//        cartDTOs.forEach(cartDTO -> {
//            productDTOs.stream().filter(productDTO -> cartDTO.getProductID() == productDTO.getId()).<UnaryOperator<Double>>map(productDTO -> v -> (double) (v + (productDTO.getPrice() * cartDTO.getAmount()))).forEach(totalMoney::updateAndGet);
//        });
        cartDTOs.forEach(cartDTO -> {
            productDTOs.forEach((ProductDTO productDTO) -> {
                if (cartDTO.getProductID() == productDTO.getId()) {
                    totalMoney.updateAndGet(v -> (double) (v + (productDTO.getPrice() * cartDTO.getAmount())));
                }
            });
        });
        double totalMoneyVAT = Math.round(totalMoney.get() * 1.1);
        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("totalMoneyVAT", totalMoneyVAT);
        model.addAttribute("listCarts", cartDTOs);
        model.addAttribute("listProducts", productDTOs);
        model.addAttribute("mess", null);
        model.addAttribute("error", null);
        model.addAttribute("invoice", new InvoiceDTO());
        model.addAttribute("option1", "onbank");
        model.addAttribute("option2", "ondelivery");
        return "order";
    }

    @PostMapping("/add_order")
    public String addOrder(Model model, @ModelAttribute("invoice") InvoiceDTO invoiceDTO, HttpSession session) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        List<CartDTO> cartDTOs = cartService.getCartDTOByAccountID(accountDTO.getId());
        List<ProductDTO> productDTOs = productService.getAllProductDTOs();
        AtomicReference<String> context = new AtomicReference<>((String) "");
        AtomicReference<Double> totalMoney = new AtomicReference<>((double) 0);
        cartDTOs.forEach(cartDTO -> {
            productDTOs.forEach((ProductDTO productDTO) -> {
                if (cartDTO.getProductID() == productDTO.getId()) {
                    totalMoney.updateAndGet(v -> (double) (v + (productDTO.getPrice() * cartDTO.getAmount())));
                    context.updateAndGet(v ->
                            (String) " Name : " + productDTO.getName()
                            + ": Color : " + productDTO.getColor()
                            + ": Size : " + cartDTO.getSize()
                            + ": Quantity : " + cartDTO.getAmount()
                            + " // ");
                }
                ;
            });
        });
        double totalMoneyVAT = Math.round(totalMoney.get() * 1.1);
        invoiceService.insertInvoiceDTO(accountDTO.getId(), invoiceDTO);
        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("totalMoneyVAT", totalMoneyVAT);
        model.addAttribute("listCarts", cartDTOs);
        model.addAttribute("listProducts", productDTOs);
        model.addAttribute("mess", null);
        model.addAttribute("error", null);
        return null;
    }

    @GetMapping("/export_excel_file")
    public String exportExcelFile() {
        return null;
    }
}
