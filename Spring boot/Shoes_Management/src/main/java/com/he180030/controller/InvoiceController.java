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
}
