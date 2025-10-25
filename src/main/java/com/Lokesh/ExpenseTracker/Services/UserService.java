package com.Lokesh.ExpenseTracker.Services;

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

    public User getUserById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
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

    public void updateAmount(Long id, Expense expense) throws RuntimeException {
        User user = getUserById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setAmount(user.getAmount().add(expense.getAmount()));
    }
}
