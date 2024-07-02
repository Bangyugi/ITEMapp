package com.bangvan.EMwebapp.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String jobTitle;
    private String workStyle;
    private LocalDate startDate;
    private Double salary;



}
