package com.carrental.whybuy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Email is Required")
    private String password;
}
