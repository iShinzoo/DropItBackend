package com.example.DropIT.service;

import com.example.DropIT.controller.Request.AuthenticationRequest;
import com.example.DropIT.controller.Response.AuthenticationResponse;
import com.example.DropIT.controller.Request.RegisterRequest;
import com.example.DropIT.entities.User;
import com.example.DropIT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService
{
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService service;

    private final AuthenticationManager manager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder().firstName(registerRequest.getFirstname())
                .lastName(registerRequest.getLastname()).email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();
        repository.save(user);
        var jwtToken= service.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest registerRequest) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getEmail(),registerRequest.getPassword()));
        var user = repository.findByEmail(registerRequest.getEmail()).orElseThrow();
        var jwtToken= service.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
