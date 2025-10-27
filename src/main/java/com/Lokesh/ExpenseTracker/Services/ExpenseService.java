package com.Lokesh.ExpenseTracker.Services;

import com.Lokesh.ExpenseTracker.Exceptions.AccessDeniedException;
import com.Lokesh.ExpenseTracker.Exceptions.ExpenseNotFoundException;
import com.Lokesh.ExpenseTracker.Exceptions.InvalidUserException;
import com.Lokesh.ExpenseTracker.Models.Expense;
import com.Lokesh.ExpenseTracker.Repo.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExpenseService {

    private final UserService userService;
    private final ExpenseRepo expenseRepo;
    @Autowired
    public ExpenseService(UserService userService, ExpenseRepo expenseRepo) {
        this.userService = userService;
        this.expenseRepo = expenseRepo;
    }

    public List<Expense> getExpensesByUserId(Long id) {
        return expenseRepo.findByUserId(id);
    }

    @Transactional
    public void addExpense(Long id, Expense expense) throws InvalidUserException {
        userService.updateAmountSpent(id, expense);
        expenseRepo.save(expense);
    }

    public Expense getExpenseByUserIdAndExpenseId(Long uid, Long eid) throws AccessDeniedException, ExpenseNotFoundException {
        return expenseRepo.findByIdAndUserId(eid, uid, Limit.of(1)).stream().findFirst().orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
    }
}
