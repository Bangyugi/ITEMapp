package com.bangvan.EMwebapp.service;

import com.bangvan.EMwebapp.dto.request.CreateCompanyRequest;
import com.bangvan.EMwebapp.dto.response.CompanyResponse;
import com.bangvan.EMwebapp.entity.Company;
import com.bangvan.EMwebapp.entity.User;
import com.bangvan.EMwebapp.repository.CompanyRepository;
import com.bangvan.EMwebapp.repository.UserRepository;
import com.bangvan.EMwebapp.util.AESutil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    public List<CompanyResponse> findAllByUser(String username) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        User user = userRepository.findByUsername(username);
        Set<User> users = new HashSet<>();
        users.add(user);
        List<Company> companies = companyRepository.findAllByUsers(users);
        List<CompanyResponse> companiesResponse = new ArrayList<>();
        for (Company company : companies) {
            company.setName(AESutil.decrypt(company.getName(),AESutil.generateKey(),AESutil.generateIv()));
            company.setAddress(AESutil.decrypt(company.getAddress(),AESutil.generateKey(),AESutil.generateIv()));
            company.setPhoneNumber(AESutil.decrypt(company.getPhoneNumber(),AESutil.generateKey(),AESutil.generateIv()));
            company.setWebsite(AESutil.decrypt(company.getWebsite(),AESutil.generateKey(),AESutil.generateIv()));
            company.setVATnumber(AESutil.decrypt(company.getVATnumber(),AESutil.generateKey(),AESutil.generateIv()));
            company.setRegisterNumber(AESutil.decrypt(company.getRegisterNumber(),AESutil.generateKey(),AESutil.generateIv()));
            company.setEmail(AESutil.decrypt(company.getEmail(),AESutil.generateKey(),AESutil.generateIv()));
            CompanyResponse companyResponse = modelMapper.map(company, CompanyResponse.class);
            companiesResponse.add(companyResponse);
        }
        return companiesResponse;
    }

    @Override
    public void createCompany(CreateCompanyRequest createCompanyRequest, String username) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        createCompanyRequest.setName(AESutil.encrypt(createCompanyRequest.getName(),AESutil.generateKey(),AESutil.generateIv()));
        createCompanyRequest.setEmail(AESutil.encrypt(createCompanyRequest.getEmail(),AESutil.generateKey(),AESutil.generateIv()));
        createCompanyRequest.setAddress(AESutil.encrypt(createCompanyRequest.getAddress(),AESutil.generateKey(),AESutil.generateIv()));
        createCompanyRequest.setWebsite(AESutil.encrypt(createCompanyRequest.getWebsite(),AESutil.generateKey(),AESutil.generateIv()));
        createCompanyRequest.setPhoneNumber(AESutil.encrypt(createCompanyRequest.getPhoneNumber(),AESutil.generateKey(),AESutil.generateIv()));
        createCompanyRequest.setVATnumber(AESutil.encrypt(createCompanyRequest.getVATnumber(),AESutil.generateKey(),AESutil.generateIv()));
        createCompanyRequest.setRegisterNumber(AESutil.encrypt(createCompanyRequest.getRegisterNumber(),AESutil.generateKey(),AESutil.generateIv()));
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
