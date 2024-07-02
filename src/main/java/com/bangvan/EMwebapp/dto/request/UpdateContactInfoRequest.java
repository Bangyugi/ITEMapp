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

public class UpdateContactInfoRequest {
    private Long id;
    private String phone;
    private String email;
    private String facebook;
    private String linkedIn;
}
