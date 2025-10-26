package com.Lokesh.ExpenseTracker.Services;

import com.Lokesh.ExpenseTracker.Exceptions.InvalidUserException;
import com.Lokesh.ExpenseTracker.Models.Expense;
import com.Lokesh.ExpenseTracker.Models.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

@Service
public class UserService {

    private List<User> users = new ArrayList<>(List.of(
            new User(1L, "Lokesh", new BigDecimal("1500.00")),
            new User(2L, "Aarav", new BigDecimal("2450.50")),
            new User(3L, "Neha", new BigDecimal("980.75")),
            new User(4L, "Riya", new BigDecimal("3050.00")),
            new User(5L, "Karan", new BigDecimal("1875.25"))
    ));

    public User getUserById(Long id) throws InvalidUserException {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElseThrow(() -> new InvalidUserException("User does not exists"));
    }

    public User addUser(User user) {
        users.add(user);
        return user;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void deleteUser(User user1) {
        users.remove(user1);
    }

    public void updateAmountSpent(Long id, Expense expense) throws InvalidUserException {
        User user = getUserById(id);
        if (user == null) {
            throw new InvalidUserException("User with id "+id+" does not exists.");
        }
        user.setAmountSpent(user.getAmountSpent().add(expense.getAmount()));
    }
}
