package com.Lokesh.ExpenseTracker.Mappers;

import com.Lokesh.ExpenseTracker.DTO.UserDTO;
import com.Lokesh.ExpenseTracker.DTO.UserRegistrationDTO;
import com.Lokesh.ExpenseTracker.Models.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .amountSpent(user.getAmountSpent())
                .build();
    }

    public static List<UserDTO> toUserDTO(List<User> users) {
        if (users == null) {
            return null;
        }

        return users
                .stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    public static User toUser(UserRegistrationDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }
}
