package com.Lokesh.ExpenseTracker.Advice;

import com.Lokesh.ExpenseTracker.Exceptions.ErrorResponse;
import com.Lokesh.ExpenseTracker.Exceptions.ExpenseNotFoundException;
import com.Lokesh.ExpenseTracker.Exceptions.AccessDeniedException;
import com.Lokesh.ExpenseTracker.Exceptions.InvalidUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<?> handleExpenseNotFoundException(ExpenseNotFoundException e) {
        ErrorResponse expenseNotFound = new ErrorResponse(LocalDateTime.now(), e.getMessage(), "Expense Not Found");
        return new ResponseEntity<>(expenseNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handlePermissionDeniedException(AccessDeniedException e) {
        ErrorResponse accessDenied = new ErrorResponse(LocalDateTime.now(), e.getMessage(), "This expense does not belong to the user");
        return new ResponseEntity<>(accessDenied, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<?> handleInvalidUserException(InvalidUserException e) {
        ErrorResponse invalidUser = new ErrorResponse(LocalDateTime.now(), e.getMessage(), "Invalid User");
        return new ResponseEntity<>(invalidUser, HttpStatus.BAD_REQUEST);
    }
}
