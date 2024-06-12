package com.bangvan.EMwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/ap1/v1")
public class HelloController {

    @GetMapping("/home")
    public String home() {
        return "index";
    }
}
