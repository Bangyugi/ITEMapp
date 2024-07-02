package com.bangvan.EMwebapp.controller;

import com.bangvan.EMwebapp.dto.request.AuthenticateRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
