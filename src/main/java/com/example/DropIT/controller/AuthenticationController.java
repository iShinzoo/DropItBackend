package com.example.DropIT.controller;
import com.example.DropIT.controller.Request.AuthenticationRequest;
import com.example.DropIT.controller.Request.RegisterRequest;
import com.example.DropIT.controller.Response.AuthenticationResponse;
import com.example.DropIT.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        return  ResponseEntity.ok(service.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return  ResponseEntity.ok(service.authenticate(request));
    }
    @GetMapping("/delivery/create")
    public String createOrder(){
        return "Order Created";
    }

}
