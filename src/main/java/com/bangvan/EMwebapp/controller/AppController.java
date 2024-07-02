package com.bangvan.EMwebapp.controller;

import com.bangvan.EMwebapp.service.CompanyService;
import com.bangvan.EMwebapp.service.EmployeeService;
import com.bangvan.EMwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
//        System.out.println("Hello world");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails);
        model.addAttribute("numberOfUser", employeeService.getNumberOfEmployee(userDetails.getUsername()));
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("numberOfCompany", companyService.countByUsers(userDetails.getUsername()));
        return "home";
    }


    @GetMapping("/forgot-password")
    public String forgotPass() {
        return "forgot-password";
    }

    @GetMapping("/leave")
    public String leave(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails);
        return "leave";
    }

    @GetMapping("/manage")
    public String manage(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails);
        return "manage";
    }
    @GetMapping("/settings")
    public String setting(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails);
        return "settings";
    }
}
