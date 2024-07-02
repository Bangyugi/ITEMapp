package com.bangvan.EMwebapp.dto.response;

import com.bangvan.EMwebapp.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String team;
    private String nationality;
    private LocalDate dob;
    private String gender;
    private String facebook;
    private String linkedIn;
    private Set<CompanyResponse> Companies;
}
