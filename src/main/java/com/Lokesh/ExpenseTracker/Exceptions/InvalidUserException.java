package com.Lokesh.ExpenseTracker.Exceptions;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String message){
        super(message);
    }
}
