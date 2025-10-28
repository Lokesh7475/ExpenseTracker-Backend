package com.Lokesh.ExpenseTracker.Repo;

import com.Lokesh.ExpenseTracker.Models.Expense;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);

    List<Expense> findByIdAndUserId(Long id, Long userId, Limit limit);

    void deleteExpenseByIdAndUserId(Long id, Long userId);
}
