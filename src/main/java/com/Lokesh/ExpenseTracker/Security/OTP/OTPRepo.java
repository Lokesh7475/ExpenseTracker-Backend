package com.Lokesh.ExpenseTracker.Security.OTP;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepo extends JpaRepository<OTP, Long> {

    void deleteByEmail(String email);

    Optional<OTP> findByEmail(String email);
}
