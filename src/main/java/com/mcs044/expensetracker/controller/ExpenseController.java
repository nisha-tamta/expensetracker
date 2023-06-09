package com.mcs044.expensetracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mcs044.expensetracker.entity.Expense;
import com.mcs044.expensetracker.service.ExpenseService;

/*
 * Create a RESTful API controller class that will handle incoming HTTP requests
 * and interact with the ExpenseService to perform CRUD operations on expenses.
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<?> getAllExpenses(@RequestParam("userId") Long userId) {
        try {
            List<Expense> expenses = expenseService.getAllExpenses(userId);
            return ResponseEntity.ok(expenses);
        } catch (Exception ex) {
            String errorMessage = "Error during getting expenses: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorMessage);
        }
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> getAllExpensesForTime(@RequestParam("userId") Long userId,
                                            @RequestParam("month") Integer month,
                                            @RequestParam("year") Integer year) {
        try {
            List<Expense> expenses = expenseService.getExpensesByMonthAndYear(userId, month, year);
            return ResponseEntity.ok(expenses);
        } catch (Exception ex) {
            String errorMessage = "Error during getting expenses: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorMessage);
        }
    }    

    @CrossOrigin
    @GetMapping("/{expenseId}")
    public ResponseEntity<?> getExpenseById(@PathVariable("expenseId") Long expenseId) {
        try {
            Expense expense = expenseService.getExpenseById(expenseId);
            if (expense != null) {
                return ResponseEntity.ok(expense);
            } else {
                String errorMessage = "Expense not found with expenseId: " + expenseId;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> addExpense(@RequestParam("userId") Long userId, @RequestBody Expense expense) {
        try {
            Expense returned = expenseService.addExpense(userId, expense);
            return ResponseEntity.ok(returned);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ex.getMessage());
        }
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable("id") Long id, @RequestParam("userId") Long userId, @RequestBody Expense expense) {
        try {
            Expense returned = expenseService.updateExpense(id, userId, expense);
            return ResponseEntity.ok(returned);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ex.getMessage());
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@RequestParam("userId") Long userId, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(expenseService.deleteExpense(userId, id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ex.getMessage());
        }
    }
}

