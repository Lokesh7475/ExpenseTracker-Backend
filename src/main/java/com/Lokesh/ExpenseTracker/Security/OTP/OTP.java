package com.Lokesh.ExpenseTracker.Security.OTP;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String otp;

    private LocalDateTime expiresAt;

    @PrePersist
    public void prePersist() {
        expiresAt = LocalDateTime.now().plusMinutes(1);
    }
}
