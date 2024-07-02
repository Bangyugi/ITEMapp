package com.bangvan.EMwebapp.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull(message = "Username is required!")
    @Size(min = 6, max = 20, message = "Username's size should be greater than 6 chars and less than 20 chars")
    private String username;

    @NotNull(message = "Email is required!")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email pattern invalid")
    private String email;

    @NotNull(message = "Password is required!")
    @Size(min = 6, max = 16, message = "Password's size should be greater than 6 chars and less than 16 chars")
    private String password;

    @NotNull(message = "Confirm password is required!")
    private String repeatPassword;

    private String otp;
}
