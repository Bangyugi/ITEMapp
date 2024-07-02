package com.bangvan.EMwebapp.service;

import com.bangvan.EMwebapp.dto.request.CreateEmployeeRequest;
import com.bangvan.EMwebapp.dto.request.UpdateEmployeeRequest;
import com.bangvan.EMwebapp.dto.response.EmployeeResponse;
import com.bangvan.EMwebapp.dto.response.UserResponse;
import com.bangvan.EMwebapp.entity.User;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponse> getAllEmployees(UserResponse user);


    int getNumberOfEmployee(String username);



    void createEmployee(CreateEmployeeRequest createEmployeeRequest, String username);


    EmployeeResponse findEmployeeById(Long id);

    void updateEmployee(UpdateEmployeeRequest updateEmployeeRequest);

    void deleteEmployee(Long id);
}
