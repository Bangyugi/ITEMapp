package com.bangvan.EMwebapp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ChangePasswordRequest {
    private Long id;
    @NotNull(message = "Old password is required")
    private String oldPassword;
    @NotNull(message = "New Password is required")
    private String newPassword;
    @NotNull(message = "Confirm password is required")
    private String confirmPassword;
}
