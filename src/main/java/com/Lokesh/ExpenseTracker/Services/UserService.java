package com.Lokesh.ExpenseTracker.Services;

import com.Lokesh.ExpenseTracker.DTO.ExpenseDTO;
import com.Lokesh.ExpenseTracker.DTO.UserDTO;
import com.Lokesh.ExpenseTracker.Exceptions.InvalidUserException;
import com.Lokesh.ExpenseTracker.Mappers.UserMapper;
import com.Lokesh.ExpenseTracker.Models.User;
import com.Lokesh.ExpenseTracker.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.*;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO getUserById(Long id) throws InvalidUserException {
        User user = userRepo
                .findById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new InvalidUserException("This user does not exists"));

        return UserMapper.toUserDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return UserMapper.toUserDTO(userRepo.findAll());
    }

    public void deleteUser(UserDTO user1) {
        userRepo.delete(userRepo.findById(user1.getId()).orElseThrow(() -> new InvalidUserException("This user does not exists")));
    }

    public void updateAmountSpent(Long id, ExpenseDTO expense, Boolean toAdd) throws InvalidUserException {
        User user = userRepo.findById(id).orElseThrow(() -> new InvalidUserException("This user does not exists"));
        if(toAdd)
            user.setAmountSpent(user.getAmountSpent().add(expense.getAmount()));
        else
            user.setAmountSpent(user.getAmountSpent().subtract(expense.getAmount()));
        userRepo.save(user);
    }

    public UserDTO updateUser(UserDTO user) {
        User oldUser = userRepo.findById(user.getId()).stream().findFirst().orElseThrow(() -> new InvalidUserException("User does not exist"));
        if(!(user.getUsername() == null || user.getUsername().isEmpty()))
            oldUser.setUsername(user.getUsername());
        if(!(user.getEmail() == null || user.getEmail().isEmpty()))
            oldUser.setEmail(user.getEmail());

        return UserMapper.toUserDTO(userRepo.save(oldUser));
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepo.findUserByUsername(username).orElseThrow(() -> new InvalidUserException("This user does not exists"));
        if(user==null)
            throw new InvalidUserException("This user does not exists");
        return UserMapper.toUserDTO(user);
    }
}
