package com.bangvan.EMwebapp.controller;


import com.bangvan.EMwebapp.dto.request.CreateCompanyRequest;
import com.bangvan.EMwebapp.dto.request.UpdateCompanyRequest;
import com.bangvan.EMwebapp.entity.Company;
import com.bangvan.EMwebapp.service.CompanyService;
import com.bangvan.EMwebapp.service.EmployeeService;
import com.bangvan.EMwebapp.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/company")
    public String company(Model model, Authentication authentication) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user",userDetails);
        model.addAttribute("companies",companyService.findAllByUser(userDetails.getUsername()));
        model.addAttribute("createCompanyRequest",new CreateCompanyRequest());
        model.addAttribute("updateCompanyRequest",new UpdateCompanyRequest());
        return "company";
    }

    @PostMapping("/add-company")
    public String addCompany(@ModelAttribute("createCompanyRequest") CreateCompanyRequest createCompanyRequest, Authentication authentication) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(createCompanyRequest);
        companyService.createCompany(createCompanyRequest, userDetails.getUsername());
        return "redirect:/company";
    }

//    @PostMapping("/update-company")
//    public String updateCompany(@ModelAttribute("company") Company company, Authentication authentication) {
//
//    }

}
