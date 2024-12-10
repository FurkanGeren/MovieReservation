package com.movie.auth_server.controller;



import com.movie.auth_server.dto.LoginDTO;
import com.movie.auth_server.dto.RegisterDTO;
import com.movie.auth_server.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response){
        authService.login(loginDTO, response);
        return ResponseEntity.ok("Logged in.");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO, HttpServletResponse response){
        authService.register(registerDTO, response);
        return ResponseEntity.ok("Registered.");
    }


}