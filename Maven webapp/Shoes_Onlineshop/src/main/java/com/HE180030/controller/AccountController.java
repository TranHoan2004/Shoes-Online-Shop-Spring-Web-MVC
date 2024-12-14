package com.HE180030.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    @GetMapping("/manage_account")
    public String manageAccount(Model model, HttpSession session) {
        return "manage-account";
    }

    @GetMapping("/login")
    public String redirectTologin(Model model, HttpSession session) {
        return "login";
    }

    @GetMapping("/logout")
    public String Logout(Model model, HttpSession session) {
        return "home";
    }
}
