package com.Lokesh.ExpenseTracker.DTO;

import com.Lokesh.ExpenseTracker.Enums.Category;
import com.Lokesh.ExpenseTracker.Enums.PaymentMethod;

import java.math.BigDecimal;

public class ExpenseRequestDTO {
    private String description;
    private Category category;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
}
