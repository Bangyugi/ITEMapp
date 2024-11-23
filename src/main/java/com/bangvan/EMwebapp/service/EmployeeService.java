package com.bangvan.EMwebapp.service;

import com.bangvan.EMwebapp.dto.request.CreateEmployeeRequest;
import com.bangvan.EMwebapp.dto.request.UpdateEmployeeRequest;
import com.bangvan.EMwebapp.dto.response.EmployeeResponse;
import com.bangvan.EMwebapp.dto.response.UserResponse;
import com.bangvan.EMwebapp.entity.User;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface EmployeeService {
    List<EmployeeResponse> getAllEmployees(UserResponse user) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;


    int getNumberOfEmployee(String username);



    void createEmployee(CreateEmployeeRequest createEmployeeRequest, String username) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;


    EmployeeResponse findEmployeeById(Long id) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    void updateEmployee(UpdateEmployeeRequest updateEmployeeRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    void deleteEmployee(Long id);
}
