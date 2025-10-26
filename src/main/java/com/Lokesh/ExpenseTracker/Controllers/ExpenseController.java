package com.Lokesh.ExpenseTracker.Controllers;

import com.Lokesh.ExpenseTracker.Models.Expense;
import com.Lokesh.ExpenseTracker.Services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    private ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/")
    public String hello(){
        return "Hello World";
    }

    @GetMapping("/users/{id}/expenses")
    public ResponseEntity<List<Expense>> getExpensesByUserId(@PathVariable Long id){
        List<Expense> expenses = expenseService.getExpensesByUserId(id);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @PostMapping("/users/{id}/expenses")
    public ResponseEntity<Expense> createExpense(@PathVariable Long id, @RequestBody Expense expense){
        expenseService.addExpense(id, expense);
        return new ResponseEntity<>(expense ,HttpStatus.CREATED);
    }

    @GetMapping("/users/{uid}/expenses/{eid}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long uid, @PathVariable Long eid){
        Expense expense = expenseService.getExpenseByUserIdAndExpenseId(uid, eid);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }
}
