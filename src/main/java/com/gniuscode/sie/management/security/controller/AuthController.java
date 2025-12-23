package com.gniuscode.sie.management.security.controller;

import com.gniuscode.sie.management.common.dto.ApiResponse;
import com.gniuscode.sie.management.security.dto.AuthResponse;
import com.gniuscode.sie.management.security.dto.LoginRequest;
import com.gniuscode.sie.management.security.dto.RegisterRequest;
import com.gniuscode.sie.management.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login (@Valid @RequestBody LoginRequest loginRequest){
        AuthResponse authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(new ApiResponse<>("Login successful", authResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest registerRequest){
        AuthResponse authResponse = authService.register(registerRequest);
        return ResponseEntity.ok(new ApiResponse<>("Register successful", authResponse));
    }
}
