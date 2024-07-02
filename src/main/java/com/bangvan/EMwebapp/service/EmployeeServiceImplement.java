package com.bangvan.EMwebapp.service;

import com.bangvan.EMwebapp.dto.request.CreateEmployeeRequest;
import com.bangvan.EMwebapp.dto.request.UpdateEmployeeRequest;
import com.bangvan.EMwebapp.dto.response.EmployeeResponse;
import com.bangvan.EMwebapp.dto.response.UserResponse;
import com.bangvan.EMwebapp.entity.Company;
import com.bangvan.EMwebapp.entity.Employee;
import com.bangvan.EMwebapp.repository.CompanyRepository;
import com.bangvan.EMwebapp.repository.EmployeeRepository;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<EmployeeResponse> getAllEmployees(UserResponse user) {
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
    public void createEmployee(CreateEmployeeRequest createEmployeeRequest, String username) {
        Employee employee = modelMapper.map(createEmployeeRequest, Employee.class);
        Set<Company> companies = new HashSet<>();
        for (String companyName : createEmployeeRequest.getCompanies()) {
            Company company = companyRepository.findByName(companyName, username);
            System.out.println(company);
            companies.add(company);
        }
        employee.setCompanies(companies);
        System.out.println(employee);
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponse findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
        return employeeResponse;
    }

    @Override
    public void updateEmployee(UpdateEmployeeRequest updateEmployeeRequest) {
        Employee employee = employeeRepository.findById(updateEmployeeRequest.getId()).orElse(null);
        modelMapper.map(updateEmployeeRequest, employee);
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);

    }
}