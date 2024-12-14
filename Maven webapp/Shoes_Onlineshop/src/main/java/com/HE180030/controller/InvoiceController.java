package com.HE180030.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InvoiceController {

    @GetMapping("/invoice")
    public String index() {
        return "invoice";
    }
}
