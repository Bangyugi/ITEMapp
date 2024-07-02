package com.bangvan.EMwebapp.dto.request;

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

public class UpdateEmployeeRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String jobTitle;
    private String workStyle;
    private Double salary;
}