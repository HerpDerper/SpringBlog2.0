package com.example.SpringRealBlog;

import org.springframework.security.core.Authentication;

public class AdminCheck {

    public boolean adminAccess(Authentication auth) {
        return auth.getAuthorities().toString().contains("ADMIN");
    }
}