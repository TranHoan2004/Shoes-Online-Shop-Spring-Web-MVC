package com.HE180030.controller;

import com.HE180030.dto.AccountDTO;
import com.HE180030.service.AccountService;
import com.HE180030.utils.EncodingPassword;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/manager_account")
    public String manageAccount(Model model, @RequestParam("page") String page) {
        List<AccountDTO> accounts = accountService.getAllAccountDTOs();
        displayProductByPage(model, accounts, page);
        return "manager-account";
    }

    @GetMapping("/login")
    public String redirectToLogin(Model model, HttpSession session, HttpServletRequest request) {
        Cookie cookie[] = request.getCookies();
        if (cookie != null) {
            for (Cookie c : cookie) {
                if (c.getName().trim().equals("userCookie")) {
                    model.addAttribute("username", c.getValue());
                }
                if (c.getName().trim().equals("passCookie")) {
                    model.addAttribute("password", c.getValue());
                }
            }
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("account");
        return "home";
    }

    @GetMapping("/edit_profile")
    public String redirectToEditProfile(Model model, HttpSession session) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");
        model.addAttribute("account", accountDTO);
        return "edit-profile";
    }

    @PostMapping("/edit_profile")
    public String editProfile(Model model, @ModelAttribute AccountDTO accountDTO) {
        accountService.updateProfile(accountDTO);
        model.addAttribute("message", "Update profile successfully");
        model.addAttribute("error", null);
        return "edit-profile";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") AccountDTO accountDTO, HttpServletRequest request, Model model, HttpSession session) {
        String encryptedPassword = EncodingPassword.getEncryptedPassword(accountDTO.getPassword());
        String username = accountDTO.getUsername();
        AccountDTO acc = accountService.login(accountDTO);
        if (acc != null) {
            model.addAttribute("error", "Wrong username or password");
            return "login";
        } else {
            session.setAttribute("account", acc);
            session.setMaxInactiveInterval(60 * 60 * 24);
            Cookie usernameCookie = new Cookie("userCookie", acc.getUsername());
            Cookie passwordCookie = new Cookie("passCookie", acc.getPassword());
        }
        return "home";
    }

    @PostMapping("/add_account")
    public String addAccount(Model model, @ModelAttribute("account") AccountDTO accountDTO) {
        String rawPassword = accountDTO.getPassword();
        String encryptedPassword = EncodingPassword.getEncryptedPassword(rawPassword);
        accountDTO.setPassword(encryptedPassword);
        if (accountService.getAccountDTOByName(accountDTO.getUsername()) == null) {
            accountService.insertAccountDTO(accountDTO);
        } else {
            model.addAttribute("mess", "User is existing!");
        }
        return "manager-account";
    }

    private void displayProductByPage(Model model, @NotNull List<AccountDTO> list, @NotNull String pageRequest) {
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
        List<AccountDTO> listByPage = accountService.getListAccountDTOsByPage(list, start, end);
        model.addAttribute("list", listByPage);
        model.addAttribute("num", num);
        model.addAttribute("page", page);
    }
}
