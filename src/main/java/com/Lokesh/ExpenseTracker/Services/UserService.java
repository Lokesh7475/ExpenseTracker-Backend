package com.Lokesh.ExpenseTracker.Services;

import com.Lokesh.ExpenseTracker.Exceptions.InvalidUserException;
import com.Lokesh.ExpenseTracker.Models.Expense;
import com.Lokesh.ExpenseTracker.Models.User;
import com.Lokesh.ExpenseTracker.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.*;

@Service
public class UserService {

    private final UserRepo userRepo;
    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUserById(Long id) throws InvalidUserException {
        return userRepo.findById(id).stream().findFirst().orElseThrow(() -> new InvalidUserException("This user does not exists"));
    }

    public User addUser(User user) {
        if(user.getAmountSpent()==null)
            user.setAmountSpent(new BigDecimal("0.00"));
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void deleteUser(User user1) {
        userRepo.delete(user1);
    }

    public void updateAmountSpent(Long id, Expense expense) throws InvalidUserException {
        User user = userRepo.findById(id).orElseThrow(() -> new InvalidUserException("This user does not exists"));
        user.setAmountSpent(user.getAmountSpent().add(expense.getAmount()));
        userRepo.save(user);
    }
}
