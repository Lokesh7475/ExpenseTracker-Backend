package com.Lokesh.ExpenseTracker.Advice;

import com.Lokesh.ExpenseTracker.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception e){
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), e.getMessage(), "Internal Server Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

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

    @ExceptionHandler(ExpenseIsNullException.class)
    public ResponseEntity<?> handleBadRequestException(ExpenseIsNullException e) {
        ErrorResponse badRequest = new ErrorResponse(LocalDateTime.now(), e.getMessage(), "Bad Request");
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ErrorResponse usernameNotFound = new ErrorResponse(LocalDateTime.now(), e.getMessage(), "Username Not Found");
        return new ResponseEntity<>(usernameNotFound, HttpStatus.NOT_FOUND);
    }
}
