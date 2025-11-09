package com.Lokesh.ExpenseTracker.Mappers;

import com.Lokesh.ExpenseTracker.DTO.ExpenseDTO;
import com.Lokesh.ExpenseTracker.DTO.ExpenseRequestDTO;
import com.Lokesh.ExpenseTracker.Models.Expense;

import java.util.List;
import java.util.stream.Collectors;


public class ExpenseMapper {

    public static ExpenseDTO toExpenseDTO(Expense expense) {
        if (expense == null) {
            return null;
        }
        return ExpenseDTO.builder()
                .id(expense.getId())
                .description(expense.getDescription())
                .category(expense.getCategory())
                .paymentMethod(expense.getPaymentMethod())
                .amount(expense.getAmount())
                .dateAndTime(expense.getDateAndTime())
                .build();
    }

    public static Expense toExpense(ExpenseRequestDTO expenseDTO) {
        if (expenseDTO == null) {
            return null;
        }

        return Expense.builder()
                .amount(expenseDTO.getAmount())
                .description(expenseDTO.getDescription())
                .category(expenseDTO.getCategory())
                .paymentMethod(expenseDTO.getPaymentMethod())
                .build();
    }

    public static List<ExpenseDTO> toExpenseDTO(List<Expense> expenses) {
        if (expenses == null) {
            return null;
        }

        return expenses.stream()
                .map(ExpenseMapper::toExpenseDTO)
                .collect(Collectors.toList());
    }
}
