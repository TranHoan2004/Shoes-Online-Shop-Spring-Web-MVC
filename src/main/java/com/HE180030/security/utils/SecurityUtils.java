package com.HE180030.security.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.logging.Logger;

public class SecurityUtils {
    public static @NotNull User getCurrentUser() {
        Logger logger = Logger.getLogger(SecurityUtils.class.getName());
        logger.info("getCurrentUser");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Current user: " + auth);
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            return (User) auth.getPrincipal();
        }
        throw new RuntimeException("No authentication found");
    }
}
