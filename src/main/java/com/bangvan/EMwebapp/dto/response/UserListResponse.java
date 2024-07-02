package com.bangvan.EMwebapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponse {
    private String name;
    private String email;
    private String team;
    private LocalDate dob;

}
