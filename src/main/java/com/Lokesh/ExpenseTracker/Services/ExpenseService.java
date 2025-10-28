package com.Lokesh.ExpenseTracker.Services;

import com.Lokesh.ExpenseTracker.Exceptions.AccessDeniedException;
import com.Lokesh.ExpenseTracker.Exceptions.ExpenseIsNullException;
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
        userService.updateAmountSpent(id, expense, true);
        expenseRepo.save(expense);
    }

    public Expense getExpenseByUserIdAndExpenseId(Long uid, Long eid) throws AccessDeniedException, ExpenseNotFoundException {
        return expenseRepo.findByIdAndUserId(eid, uid, Limit.of(1)).stream().findFirst().orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
    }

    @Transactional
    public void deleteExpense(Long id, Expense expense) throws ExpenseIsNullException{
        if(expense == null)
            throw new ExpenseIsNullException("Expense object is not in the request");
        expenseRepo.deleteExpenseByIdAndUserId(expense.getId(), id);
        userService.updateAmountSpent(id, expense, false);
    }

    @Transactional
    public void updateExpense(Long id, Expense expense) throws ExpenseIsNullException{
        if(expense == null)
            throw new ExpenseIsNullException("Expense object is not in the request");
        Expense oldExpense = getExpenseByUserIdAndExpenseId(id, expense.getId());
        userService.updateAmountSpent(id, oldExpense, false);
        userService.updateAmountSpent(id, expense, true);
        expenseRepo.save(expense);
    }
}
