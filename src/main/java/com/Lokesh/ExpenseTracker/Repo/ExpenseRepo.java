package com.Lokesh.ExpenseTracker.Repo;

import com.Lokesh.ExpenseTracker.Models.Expense;
import com.Lokesh.ExpenseTracker.Models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    Page<Expense> findByUser(User user, Pageable pageable);
}
