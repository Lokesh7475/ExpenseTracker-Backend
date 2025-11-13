package com.Lokesh.ExpenseTracker.Services;

import com.Lokesh.ExpenseTracker.DTO.ExpenseDTO;
import com.Lokesh.ExpenseTracker.DTO.ExpenseRequestDTO;
import com.Lokesh.ExpenseTracker.Exceptions.AccessDeniedException;
import com.Lokesh.ExpenseTracker.Exceptions.ExpenseIsNullException;
import com.Lokesh.ExpenseTracker.Exceptions.ExpenseNotFoundException;
import com.Lokesh.ExpenseTracker.Exceptions.InvalidUserException;
import com.Lokesh.ExpenseTracker.Mappers.ExpenseMapper;
import com.Lokesh.ExpenseTracker.Models.Expense;
import com.Lokesh.ExpenseTracker.Models.User;
import com.Lokesh.ExpenseTracker.Repo.ExpenseRepo;
import com.Lokesh.ExpenseTracker.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExpenseService {

    private final UserService userService;
    private final ExpenseRepo expenseRepo;
    private final UserRepo userRepo;

    @Autowired
    public ExpenseService(UserService userService, ExpenseRepo expenseRepo, UserRepo userRepo) {
        this.userService = userService;
        this.expenseRepo = expenseRepo;
        this.userRepo = userRepo;
    }

    public List<ExpenseDTO> getExpensesByUserId(Long id) {
        return ExpenseMapper.toExpenseDTO(expenseRepo.findByUserId(id));
    }

    @Transactional
    public ExpenseDTO addExpense(Long id, ExpenseRequestDTO expense) throws InvalidUserException {
        Expense ex = ExpenseMapper.toExpense(expense);
        ex.setUser(userRepo.findById(id).stream().findFirst().orElseThrow(()->new InvalidUserException("User not found")));
        userService.updateAmountSpent(id, ExpenseMapper.toExpenseDTO(ex), true);
        expenseRepo.save(ex);
        return ExpenseMapper.toExpenseDTO(ex);
    }

    public ExpenseDTO getExpenseByUserIdAndExpenseId(Long uid, Long eid) throws AccessDeniedException, ExpenseNotFoundException {
        User user = userRepo
                        .findById(uid)
                        .stream()
                        .findFirst()
                        .orElseThrow(()->new InvalidUserException("User not found"));

        Expense expense = user
                        .getExpenses()
                        .stream()
                        .filter(ex -> ex.getId().equals(eid))
                        .findFirst()
                        .orElseThrow(() -> new ExpenseNotFoundException("Expense not found or this expense does not belong to the user"));

        return ExpenseMapper.toExpenseDTO(expense);
    }

    @Transactional
    public ExpenseDTO updateExpense(Long id, ExpenseDTO expense) throws ExpenseIsNullException{
        if(expense == null)
            throw new ExpenseIsNullException("Expense object is not in the request");
        ExpenseDTO oldExpense = getExpenseByUserIdAndExpenseId(id, expense.getId());
        userService.updateAmountSpent(id, oldExpense, false);
        userService.updateAmountSpent(id, expense, true);

        Expense oldExp = expenseRepo.findById(expense.getId()).orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
        if(expense.getAmount() != null)
            oldExp.setAmount(expense.getAmount());
        oldExp.setDescription(expense.getDescription());
        oldExp.setCategory(expense.getCategory());
        oldExp.setPaymentMethod(expense.getPaymentMethod());
        expenseRepo.save(oldExp);

        return ExpenseMapper.toExpenseDTO(oldExp);
    }

    @Transactional
    public void deleteExpense(Long uid, Long eid) throws ExpenseNotFoundException {
        User user = userRepo.findById(uid).orElseThrow(() -> new InvalidUserException("User id is not valid"));
        Expense expense = expenseRepo.findById(eid).orElseThrow(() -> new ExpenseNotFoundException("Expense id not found"));

        if(!expense.getUser().getId().equals(user.getId()))
            throw new AccessDeniedException("This expense does not belong to the user");

        userService.updateAmountSpent(uid, ExpenseMapper.toExpenseDTO(expense), false);
        user.getExpenses().remove(expense);
        expenseRepo.delete(expense);
    }
}
