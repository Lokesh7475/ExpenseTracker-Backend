package com.Lokesh.ExpenseTracker.Models;

import com.Lokesh.ExpenseTracker.Enums.Category;
import com.Lokesh.ExpenseTracker.Enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="expenses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(nullable=false, updatable=false)
    private LocalDateTime dateAndTime;

    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private BigDecimal amount;

    @PrePersist
    protected void onCreate() {
        dateAndTime = LocalDateTime.now();
    }
}
