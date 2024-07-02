package com.bangvan.EMwebapp.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyResponse {
    private Long id;
    private String name;
    private String address;
    private String registerNumber;
    private LocalDate incorporationDate;
    private String VATnumber;
    private String phoneNumber;
    private String email;
    private String website;
    private Set<EmployeeResponse> employees;
}
