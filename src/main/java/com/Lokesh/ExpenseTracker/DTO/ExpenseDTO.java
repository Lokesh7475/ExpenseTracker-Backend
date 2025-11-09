package com.Lokesh.ExpenseTracker.DTO;

import com.Lokesh.ExpenseTracker.Enums.Category;
import com.Lokesh.ExpenseTracker.Enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseDTO {
    private Long id;
    private String description;
    private Category category;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private LocalDateTime dateAndTime;
}
