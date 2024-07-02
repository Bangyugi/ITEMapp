package com.bangvan.EMwebapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompanyRequest {
    private Long id;
    private String name;
    private String address;
    private String registerNumber;
    private LocalDate incorporationDate;
    private String VATnumber;
    private String phoneNumber;
    private String email;
    private String website;
}
