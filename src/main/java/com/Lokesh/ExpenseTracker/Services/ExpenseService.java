package com.Lokesh.ExpenseTracker.Services;

import com.Lokesh.ExpenseTracker.Exceptions.AccessDeniedException;
import com.Lokesh.ExpenseTracker.Exceptions.ExpenseNotFoundException;
import com.Lokesh.ExpenseTracker.Exceptions.InvalidUserException;
import com.Lokesh.ExpenseTracker.Models.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private UserService userService;
    @Autowired
    public ExpenseService(UserService userService) {
        this.userService = userService;
    }

    private List<Expense> expenses = new ArrayList<>(List.of(
            new Expense(1L, 1L, LocalDateTime.now().minusDays(1),
                    "Lunch at Cafe", "Food", "UPI", new BigDecimal("250.00")),

            new Expense(2L, 1L, LocalDateTime.now().minusDays(2),
                    "Bus Ticket", "Transport", "Cash", new BigDecimal("40.00")),

            new Expense(3L, 2L, LocalDateTime.now().minusHours(10),
                    "Groceries at SuperMart", "Groceries", "Credit Card", new BigDecimal("650.45")),

            new Expense(4L, 3L, LocalDateTime.now().minusDays(5),
                    "Movie Night", "Entertainment", "Debit Card", new BigDecimal("320.00")),

            new Expense(5L, 4L, LocalDateTime.now().minusDays(3),
                    "Gym Membership", "Fitness", "UPI", new BigDecimal("1200.00")),

            new Expense(6L, 2L, LocalDateTime.now().minusDays(6),
                    "Fuel for Bike", "Transport", "Cash", new BigDecimal("550.00")),

            new Expense(7L, 5L, LocalDateTime.now().minusHours(6),
                    "Electricity Bill", "Utilities", "Online", new BigDecimal("900.75")),

            new Expense(8L, 3L, LocalDateTime.now().minusDays(1),
                    "Coffee with friends", "Food", "Cash", new BigDecimal("180.00")),

            new Expense(9L, 4L, LocalDateTime.now().minusDays(8),
                    "Amazon Purchase", "Shopping", "Credit Card", new BigDecimal("1100.00")),

            new Expense(10L, 5L, LocalDateTime.now().minusDays(2),
                    "Doctor Visit", "Health", "UPI", new BigDecimal("500.00"))
    ));


    public List<Expense> getExpensesByUserId(Long id) {
        return expenses.stream().filter(expense -> expense.getUserId().equals(id)).toList();
    }

    public void addExpense(Long id, Expense expense) throws InvalidUserException {
        userService.updateAmountSpent(id, expense);
        expenses.add(expense);
    }

    public Expense getExpenseByUserIdAndExpenseId(Long uid, Long eid) throws AccessDeniedException, ExpenseNotFoundException {
        Expense expense = expenses
                .stream()
                .filter(exp -> exp.getId().equals(eid))
                .findFirst()
                .orElse(null);

        if(expense == null)
            throw new ExpenseNotFoundException("Expense with id:"+eid+" not found");

        if(!expense.getUserId().equals(uid))
            throw new AccessDeniedException("Access Denied");

        return expense;
    }
}
