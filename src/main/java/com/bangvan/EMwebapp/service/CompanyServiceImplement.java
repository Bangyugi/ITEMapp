package com.bangvan.EMwebapp.service;

import com.bangvan.EMwebapp.dto.request.CreateCompanyRequest;
import com.bangvan.EMwebapp.dto.response.CompanyResponse;
import com.bangvan.EMwebapp.entity.Company;
import com.bangvan.EMwebapp.entity.User;
import com.bangvan.EMwebapp.repository.CompanyRepository;
import com.bangvan.EMwebapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CompanyServiceImplement implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CompanyResponse> findAllByUser(String username){
        User user = userRepository.findByUsername(username);
        Set<User> users = new HashSet<>();
        users.add(user);
        List<Company> companies = companyRepository.findAllByUsers(users);
        List<CompanyResponse> companiesResponse = new ArrayList<>();
        for (Company company : companies) {
            CompanyResponse companyResponse = modelMapper.map(company, CompanyResponse.class);
            companiesResponse.add(companyResponse);
        }
        return companiesResponse;
    }

    @Override
    public void createCompany(CreateCompanyRequest createCompanyRequest, String username) {
        Company company = modelMapper.map(createCompanyRequest, Company.class);
        User user = userRepository.findByUsername(username);
        Set<User> users = new HashSet<>();
        users.add(user);
        company.setUsers(users);
        System.out.println(company);
        companyRepository.save(company);
    }

    @Override
    public Long countByUsers(String username){
        User user = userRepository.findByUsername(username);
        Set<User> users = new HashSet<>();
        users.add(user);
        List<Company> companies =companyRepository.findAllByUsers(users);
        return (long) companies.size();
    }
}
