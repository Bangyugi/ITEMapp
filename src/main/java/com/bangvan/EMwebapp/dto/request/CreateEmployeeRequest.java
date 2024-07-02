package com.bangvan.EMwebapp.dto.request;

import jakarta.validation.constraints.NotNull;
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

public class CreateEmployeeRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String jobTitle;
    private String workStyle;
    private LocalDate startDate;
    private Double salary;
    private Set<String> companies;
}
