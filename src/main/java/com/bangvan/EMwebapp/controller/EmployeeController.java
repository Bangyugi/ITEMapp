package com.bangvan.EMwebapp.controller;

import com.bangvan.EMwebapp.dto.request.CreateEmployeeRequest;
import com.bangvan.EMwebapp.dto.request.UpdateEmployeeRequest;
import com.bangvan.EMwebapp.dto.response.EmployeeResponse;
import com.bangvan.EMwebapp.dto.response.UserListResponse;
import com.bangvan.EMwebapp.dto.response.UserResponse;
import com.bangvan.EMwebapp.entity.Company;
import com.bangvan.EMwebapp.entity.Employee;
import com.bangvan.EMwebapp.service.EmployeeService;
import com.bangvan.EMwebapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class EmployeeController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/employee")
    public String employee(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails);

        UserResponse userResponse = userService.getUserByUsername(userDetails.getUsername());



        List<EmployeeResponse> employees = employeeService.getAllEmployees(userResponse);
        System.out.println(employees);
        model.addAttribute("employees", employees);
        model.addAttribute("numberOfUsers", employees.size() +" People");

        return "employee";
    }

    @GetMapping("/add-employee")
    public String addEmployee(Model model,Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails);
        model.addAttribute("employee", new CreateEmployeeRequest());
        UserResponse userResponse = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("companiesOwn",userResponse.getCompanies());
        System.out.println(userResponse.getCompanies());
        return "add-employee";
    }

    @PostMapping("/add-employee")
    public String createEmployee(Model model,@ModelAttribute("employee") CreateEmployeeRequest createEmployeeRequest,Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        employeeService.createEmployee(createEmployeeRequest,userDetails.getUsername());
        System.out.println(createEmployeeRequest);
        return "redirect:/employee";
    }

    @GetMapping("/update-employee")
    public String updateEmployee(@RequestParam("employeeId") Long id, Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user",userDetails);
        EmployeeResponse employee = employeeService.findEmployeeById(id);
        UpdateEmployeeRequest updateEmployeeRequest = modelMapper.map(employee, UpdateEmployeeRequest.class);
        model.addAttribute("employee", updateEmployeeRequest);
        return "update-employee";
    }

    @PostMapping("/update-employee")
    public String updateEmployee(Model model, @ModelAttribute("employee") UpdateEmployeeRequest updateEmployeeRequest, Authentication authentication){
        System.out.println(updateEmployeeRequest);
        employeeService.updateEmployee(updateEmployeeRequest);
        return "redirect:/employee";
    }

    @GetMapping("/delete-employee")
    public String deleteEmployee(@RequestParam("employeeId") Long id, Model model, Authentication authentication) {
        employeeService.deleteEmployee(id);
        return "redirect:/employee";
    }

}
