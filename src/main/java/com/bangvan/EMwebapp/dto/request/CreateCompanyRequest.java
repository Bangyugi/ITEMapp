package com.bangvan.EMwebapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateCompanyRequest {

    private String name;

    private String address;

    private String registerNumber;

    private LocalDate incorporationDate;

    private String VATnumber;

    private String phoneNumber;

    private String email;

    private String website;




}
