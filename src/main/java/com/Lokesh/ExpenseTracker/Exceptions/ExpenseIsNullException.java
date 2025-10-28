package com.Lokesh.ExpenseTracker.Exceptions;

public class ExpenseIsNullException extends RuntimeException {
    public ExpenseIsNullException(String message) {
        super(message);
    }
}
