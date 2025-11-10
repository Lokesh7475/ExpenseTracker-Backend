package com.Lokesh.ExpenseTracker.Repo;

import com.Lokesh.ExpenseTracker.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findByEmail(String email);
}
