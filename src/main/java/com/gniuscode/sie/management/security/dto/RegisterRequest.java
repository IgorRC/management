package com.gniuscode.sie.management.security.dto;

import com.gniuscode.sie.management.security.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;

@Data
public class RegisterRequest {
    @NotNull(message = "Username is required")
    @Size(min = 3, max = 20,message = "Username must be between 3 and 20 characters")
    private String username;

    @NotNull(message = "Email is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String email;

    @NotNull(message = "Password id required")
    private String password;

    private Set<Role> roles;
}
