package com.Lokesh.ExpenseTracker.Security;

import com.Lokesh.ExpenseTracker.DTO.LoginRequestDTO;
import com.Lokesh.ExpenseTracker.DTO.LoginResponseDTO;
import com.Lokesh.ExpenseTracker.DTO.UserDTO;
import com.Lokesh.ExpenseTracker.DTO.UserRegistrationDTO;
import com.Lokesh.ExpenseTracker.Mappers.UserMapper;
import com.Lokesh.ExpenseTracker.Models.User;
import com.Lokesh.ExpenseTracker.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, AuthUtil authUtil, UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.authUtil = authUtil;
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(user);
        return new LoginResponseDTO(token, user.getId());
    }

    public UserDTO signup(UserRegistrationDTO userRegistrationDTO) {
        User user = userRepo.findUserByUsername(userRegistrationDTO.getUsername()).orElse(null);
        if (user != null) {
            throw new IllegalArgumentException("Username is already in use");
        }
        user = userRepo.findByEmail(userRegistrationDTO.getEmail()).orElse(null);
        if (user != null) {
            throw new IllegalArgumentException("Email is already in use");
        }
        user = userRepo.save(User.builder()
                .username(userRegistrationDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword()))
                .email(userRegistrationDTO.getEmail())
                .build());
        return UserMapper.toUserDTO(user);
    }
}
