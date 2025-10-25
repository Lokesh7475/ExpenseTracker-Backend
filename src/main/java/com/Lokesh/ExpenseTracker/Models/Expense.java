package com.Lokesh.ExpenseTracker.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private Long id;
    private Long userId;

    private LocalDateTime dateAndTime;
    private String description;
    private String category;

    private String paymentMethod;
    private BigDecimal amount;

}
