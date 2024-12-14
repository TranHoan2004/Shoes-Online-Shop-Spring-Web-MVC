package com.HE180030.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditProfileController {

    @GetMapping("/edit_profile")
    public String editProfile(Model model, HttpSession session) {
        return "edit-profile";
    }
}
