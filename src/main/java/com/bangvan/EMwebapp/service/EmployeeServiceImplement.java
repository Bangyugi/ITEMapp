package com.bangvan.EMwebapp.service;

import com.bangvan.EMwebapp.dto.request.CreateEmployeeRequest;
import com.bangvan.EMwebapp.dto.request.UpdateEmployeeRequest;
import com.bangvan.EMwebapp.dto.response.EmployeeResponse;
import com.bangvan.EMwebapp.dto.response.UserResponse;
import com.bangvan.EMwebapp.entity.Company;
import com.bangvan.EMwebapp.entity.Employee;
import com.bangvan.EMwebapp.repository.CompanyRepository;
import com.bangvan.EMwebapp.repository.EmployeeRepository;
import com.bangvan.EMwebapp.util.AESutil;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeServiceImplement implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CompanyRepository companyRepository;


    @Override
    public List<EmployeeResponse> getAllEmployees(UserResponse user) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        List<Tuple> results = employeeRepository.findAllEmployeesByUsername(user.getUsername());
        List<EmployeeResponse> employees = new ArrayList<>();
        for (Tuple result : results) {
            EmployeeResponse employee = new EmployeeResponse();
            employee.setId(result.get("id", Long.class));
            employee.setFirstName(result.get("first_name", String.class));
            employee.setLastName(result.get("last_name", String.class));
            employee.setEmail(result.get("email", String.class));
            employee.setJobTitle(result.get("job_title", String.class));
            employee.setWorkStyle(result.get("work_style", String.class));
            Date sqlDate = result.get("start_date", Date.class);
            if (sqlDate != null) {
                employee.setStartDate(LocalDate.parse(sqlDate.toString()));
            }
            employee.setSalary(result.get("salary", Double.class));

            employee.setJobTitle(AESutil.decrypt(employee.getJobTitle(), AESutil.generateKey(), AESutil.generateIv()));
            employee.setWorkStyle(AESutil.decrypt(employee.getWorkStyle(),AESutil.generateKey(),AESutil.generateIv()));
            employee.setFirstName(AESutil.decrypt(employee.getFirstName(),AESutil.generateKey(),AESutil.generateIv()));
            employee.setLastName(AESutil.decrypt(employee.getLastName(),AESutil.generateKey(),AESutil.generateIv()));
            employee.setEmail(AESutil.decrypt(employee.getEmail(),AESutil.generateKey(),AESutil.generateIv()));

            employees.add(employee);
        }
        return employees;
    }

    @Override
    public int getNumberOfEmployee(String username) {
        int results = employeeRepository.countEmployeesByUsername(username);
        return results;
    }

    @Override
    public void createEmployee(CreateEmployeeRequest createEmployeeRequest, String username) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        createEmployeeRequest.setFirstName(AESutil.encrypt(createEmployeeRequest.getFirstName(),AESutil.generateKey(),AESutil.generateIv()));
        createEmployeeRequest.setLastName(AESutil.encrypt(createEmployeeRequest.getLastName(),AESutil.generateKey(),AESutil.generateIv()));
        createEmployeeRequest.setEmail(AESutil.encrypt(createEmployeeRequest.getEmail(),AESutil.generateKey(),AESutil.generateIv()));
        createEmployeeRequest.setJobTitle(AESutil.encrypt(createEmployeeRequest.getJobTitle(),AESutil.generateKey(),AESutil.generateIv()));
        createEmployeeRequest.setWorkStyle(AESutil.encrypt(createEmployeeRequest.getWorkStyle(),AESutil.generateKey(),AESutil.generateIv()));

        Employee employee = modelMapper.map(createEmployeeRequest, Employee.class);
        Set<Company> companies = new HashSet<>();
        for (String companyName : createEmployeeRequest.getCompanies()) {
            companyName = AESutil.encrypt(companyName,AESutil.generateKey(),AESutil.generateIv());
            Company company = companyRepository.findByName(companyName, username);

            System.out.println(company);
            companies.add(company);
        }
        employee.setCompanies(companies);
        System.out.println(employee);
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponse findEmployeeById(Long id) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Employee employee = employeeRepository.findById(id).orElse(null);
        employee.setJobTitle(AESutil.decrypt(employee.getJobTitle(), AESutil.generateKey(), AESutil.generateIv()));
        employee.setWorkStyle(AESutil.decrypt(employee.getWorkStyle(),AESutil.generateKey(),AESutil.generateIv()));
        employee.setFirstName(AESutil.decrypt(employee.getFirstName(),AESutil.generateKey(),AESutil.generateIv()));
        employee.setLastName(AESutil.decrypt(employee.getLastName(),AESutil.generateKey(),AESutil.generateIv()));
        employee.setEmail(AESutil.decrypt(employee.getEmail(),AESutil.generateKey(),AESutil.generateIv()));

        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
        return employeeResponse;
    }

    @Override
    public void updateEmployee(UpdateEmployeeRequest updateEmployeeRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        Employee employee = employeeRepository.findById(updateEmployeeRequest.getId()).orElse(null);
        updateEmployeeRequest.setFirstName(AESutil.encrypt(updateEmployeeRequest.getFirstName(),AESutil.generateKey(),AESutil.generateIv()));
        updateEmployeeRequest.setLastName(AESutil.encrypt(updateEmployeeRequest.getLastName(),AESutil.generateKey(),AESutil.generateIv()));
        updateEmployeeRequest.setEmail(AESutil.encrypt(updateEmployeeRequest.getEmail(),AESutil.generateKey(),AESutil.generateIv()));
        updateEmployeeRequest.setJobTitle(AESutil.encrypt(updateEmployeeRequest.getJobTitle(),AESutil.generateKey(),AESutil.generateIv()));
        updateEmployeeRequest.setWorkStyle(AESutil.encrypt(updateEmployeeRequest.getWorkStyle(),AESutil.generateKey(),AESutil.generateIv()));

        modelMapper.map(updateEmployeeRequest, employee);
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);

    }
}