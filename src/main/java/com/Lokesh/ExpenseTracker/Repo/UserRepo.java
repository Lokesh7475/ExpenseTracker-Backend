package com.Lokesh.ExpenseTracker.Repo;

import com.Lokesh.ExpenseTracker.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
}
