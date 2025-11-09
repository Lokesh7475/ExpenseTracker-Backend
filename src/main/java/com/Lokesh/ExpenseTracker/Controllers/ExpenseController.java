package com.Lokesh.ExpenseTracker.Controllers;

import com.Lokesh.ExpenseTracker.DTO.ExpenseDTO;
import com.Lokesh.ExpenseTracker.DTO.ExpenseRequestDTO;
import com.Lokesh.ExpenseTracker.Services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/")
    public String hello(){
        return "Hello World";
    }

    @GetMapping("/users/{id}/expenses")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByUserId(@PathVariable Long id){
        List<ExpenseDTO> expenses = expenseService.getExpensesByUserId(id);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @PostMapping("/users/{id}/expenses")
    public ResponseEntity<ExpenseDTO> createExpense(@PathVariable Long id, @RequestBody ExpenseRequestDTO expense){
        return new ResponseEntity<>(expenseService.addExpense(id, expense) ,HttpStatus.CREATED);
    }

    @GetMapping("/users/{uid}/expenses/{eid}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long uid, @PathVariable Long eid){
        ExpenseDTO expense = expenseService.getExpenseByUserIdAndExpenseId(uid, eid);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @DeleteMapping("/users/{uid}/expenses/{eid}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long uid, @PathVariable Long eid) {
        expenseService.deleteExpense(uid, eid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/users/{id}/expenses")
    public ResponseEntity<?> updateExpense(@PathVariable Long id, @RequestBody ExpenseDTO expense){
        return new ResponseEntity<>(expenseService.updateExpense(id, expense), HttpStatus.OK);
    }
}
