package com.bangvan.EMwebapp.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBasicInfoRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String nationality;
    private LocalDate dob;
    private String gender;

}
