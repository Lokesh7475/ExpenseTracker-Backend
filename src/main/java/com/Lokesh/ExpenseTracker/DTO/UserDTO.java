package com.Lokesh.ExpenseTracker.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private BigDecimal amountSpent;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
