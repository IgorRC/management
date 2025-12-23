package com.gniuscode.sie.management.security.service;

import com.gniuscode.sie.management.common.exception.ResourceNotFoundException;
import com.gniuscode.sie.management.security.dto.AuthResponse;
import com.gniuscode.sie.management.security.dto.LoginRequest;
import com.gniuscode.sie.management.security.dto.RegisterRequest;
import com.gniuscode.sie.management.security.entity.Role;
import com.gniuscode.sie.management.security.entity.User;
import com.gniuscode.sie.management.security.repository.RoleRepository;
import com.gniuscode.sie.management.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthResponse login(LoginRequest loginRequest) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        var userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToke = jwtService.generateToken(userDetails);

        return new AuthResponse(jwtToke,userDetails.getUsername());
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new RuntimeException("Username already exists");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "username", "ROLE_USER"));

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Set.of(userRole));
        user.setEnabled(true);
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String jwtToke = jwtService.generateToken(userDetails);
        return new AuthResponse(jwtToke,user.getUsername());
    }
}
