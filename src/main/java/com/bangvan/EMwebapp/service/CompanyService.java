package com.bangvan.EMwebapp.service;

import com.bangvan.EMwebapp.dto.request.CreateCompanyRequest;
import com.bangvan.EMwebapp.dto.response.CompanyResponse;

import java.util.List;

public interface CompanyService {
    List<CompanyResponse> findAllByUser(String username);

    void createCompany(CreateCompanyRequest createCompanyRequest, String username);

    Long countByUsers(String username);
}
