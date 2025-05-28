package com.example.diploma.dto.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotNull(message = "Password should not be null")
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;
    @NotNull(message = "First name should not be null")
    @NotEmpty(message = "First name should not be empty")
    private String firstName;
    @NotNull(message = "Last name should not be null")
    @NotEmpty(message = "Last name should not be empty")
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
}
