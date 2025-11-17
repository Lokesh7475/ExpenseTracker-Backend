package com.Lokesh.ExpenseTracker.Security;

import com.Lokesh.ExpenseTracker.DTO.LoginRequestDTO;
import com.Lokesh.ExpenseTracker.DTO.LoginResponseDTO;
import com.Lokesh.ExpenseTracker.DTO.UserDTO;
import com.Lokesh.ExpenseTracker.DTO.UserRegistrationDTO;
import com.Lokesh.ExpenseTracker.Email.EmailQueueProducer;
import com.Lokesh.ExpenseTracker.Exceptions.InvalidUserException;
import com.Lokesh.ExpenseTracker.Mappers.UserMapper;
import com.Lokesh.ExpenseTracker.Models.User;
import com.Lokesh.ExpenseTracker.Repo.UserRepo;
import com.Lokesh.ExpenseTracker.Security.OTP.OTPService;
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
    private final OTPService otpService;
    private final EmailQueueProducer emailQueueProducer;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, AuthUtil authUtil, UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder, OTPService otpService, EmailQueueProducer emailQueueProducer) {
        this.authenticationManager = authenticationManager;
        this.authUtil = authUtil;
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.otpService = otpService;
        this.emailQueueProducer = emailQueueProducer;
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

    public String sendOTP(String email) throws InvalidUserException {
        userRepo.findByEmail(email).orElseThrow(() -> new InvalidUserException("User with this email does not exists"));

        String otp = otpService.generateOTP(email);
        emailQueueProducer.sendOTPMessage(email, otp);

        return "OTP sent to Email";
    }

    public String verifyOTP(String email, String otp) {
        boolean isValid = otpService.validateOTP(email, otp);

        if(!isValid) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        User user = userRepo.findByEmail(email).orElseThrow(() -> new InvalidUserException("User with this email does not exists"));
        user.setEnabled(true);
        user.setEmailVerified(true);
        userRepo.save(user);

        otpService.deleteOTP(email);

        return "User Verified";
    }
}
