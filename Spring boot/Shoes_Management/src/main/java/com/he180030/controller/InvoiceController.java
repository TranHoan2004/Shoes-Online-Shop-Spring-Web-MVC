package com.HE180030.controller;

import com.HE180030.controller.utils.CartAndProductManager;
import com.HE180030.dto.AccountDTO;
import com.HE180030.dto.InvoiceDTO;
import com.HE180030.dto.ProductDTO;
import com.HE180030.dto.SearchForm;
import com.HE180030.service.AccountService;
import com.HE180030.service.InvoiceService;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class InvoiceController {
    private final HttpSession session;
    private final InvoiceService invoiceService;
    private final AccountService accountService;
    private final CartAndProductManager manager;

    public InvoiceController(InvoiceService invoiceService, AccountService accountService, CartAndProductManager manager, HttpSession session) {
        this.invoiceService = invoiceService;
        this.accountService = accountService;
        this.manager = manager;
        this.session = session;
    }

    @GetMapping("/invoice")
    public String redirectToInvoice(Model model) {
        int accountID = getAccountID();
        List<InvoiceDTO> invoices = invoiceService.getAllInvoiceByID(accountID);
        List<AccountDTO> accounts = accountService.getAllAccountDTOs();
        model.addAttribute("listAllInvoices", invoices);
        model.addAttribute("listAllAccounts", accounts);
        return "invoice";
    }

    @GetMapping("/order")
    public String redirectToOrderScreen(Model model) {
        AtomicReference<Double> totalMoney = new AtomicReference<>((double) 0);
        manager.getCartDTOs(getAccountID()).forEach(cartDTO -> manager.getProductDTOs().forEach((ProductDTO productDTO) -> {
            if (cartDTO.getProductID() == productDTO.getId()) {
                totalMoney.updateAndGet(v -> (v + (productDTO.getPrice() * cartDTO.getAmount())));
            }
        }));
        displayContent(model, totalMoney);
        model.addAttribute("invoice", new InvoiceDTO());
        model.addAttribute("option1", "onbank");
        model.addAttribute("option2", "ondelivery");
        model.addAttribute("searchForm", new SearchForm());
        return "order";
    }

    @PostMapping("/addOrder")
    public String addOrder(Model model, @ModelAttribute("invoice") InvoiceDTO invoiceDTO) {
        StringBuilder context = new StringBuilder();
        AtomicReference<Double> totalMoney = new AtomicReference<>((double) 0);
        manager.getCartDTOs(getAccountID()).forEach(cartDTO ->
                manager.getProductDTOs().forEach((ProductDTO productDTO) -> {
                    if (cartDTO.getProductID() == productDTO.getId()) {
                        totalMoney.updateAndGet(v -> (v + (productDTO.getPrice() * cartDTO.getAmount())));
                        context.append(" Name : ").append(productDTO.getName())
                                .append(": Color : ").append(productDTO.getColor())
                                .append(" Name : ").append(cartDTO.getSize())
                                .append(": Quantity : ").append(cartDTO.getAmount())
                                .append(" // ");
                    }
                }));
        invoiceService.insertInvoiceDTO(getAccountID(), invoiceDTO, context.toString());
        displayContent(model, totalMoney);
        return "home";
    }

    @GetMapping("/exportExcelFile")
    public String exportExcelFile() {
        return null;
    }

    @GetMapping("/searchAjaxHoaDon")
    public String searchAjaxHoaDon() {
        return null;
    }

    private void displayContent(@NotNull Model model,
                                @NotNull AtomicReference<Double> totalMoney) {
        double totalMoneyVAT = Math.round(totalMoney.get() * 1.1);
        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("totalMoneyVAT", totalMoneyVAT);
        model.addAttribute("listCarts", manager.getCartDTOs(getAccountID()));
        model.addAttribute("listProducts", manager.getProductDTOs());
    }

    private int getAccountID() {
        AccountDTO account = (AccountDTO) session.getAttribute("account");
        return account.getId();
    }
}
