package com.Lokesh.ExpenseTracker.Controllers;

import com.Lokesh.ExpenseTracker.DTO.LoginRequestDTO;
import com.Lokesh.ExpenseTracker.DTO.LoginResponseDTO;
import com.Lokesh.ExpenseTracker.DTO.UserDTO;
import com.Lokesh.ExpenseTracker.DTO.UserRegistrationDTO;
import com.Lokesh.ExpenseTracker.Security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return ResponseEntity.ok(authService.signup(userRegistrationDTO));
    }
}
