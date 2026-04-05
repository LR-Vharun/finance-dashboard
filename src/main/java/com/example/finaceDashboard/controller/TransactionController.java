package com.example.finaceDashboard.controller;

import com.example.finaceDashboard.entity.Transaction;
import com.example.finaceDashboard.security.CustomUserDetails;
import com.example.finaceDashboard.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // ── TRANSACTION ENDPOINTS ────────────────────────────────────────

    @PostMapping("/api/transactions")
    public ResponseEntity<?> createTransaction(
            @RequestBody Transaction transaction,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Long userId = userDetails.getId();
            Transaction saved = transactionService.createTransaction(transaction, userId);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/transactions")
    public ResponseEntity<?> getAllTransactions(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Long userId = userDetails.getId();
            List<Transaction> txns = transactionService.getTransactionsByUser(userId);
            return ResponseEntity.ok(txns);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping("/api/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok("Transaction deleted successfully");
    }

    // ── DASHBOARD ENDPOINTS (per-user) ───────────────────────────────

    @GetMapping("/api/dashboard/total-income")
    public ResponseEntity<?> getTotalIncome(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Long userId = userDetails.getId();
            return ResponseEntity.ok(transactionService.getTotalIncomeByUser(userId));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/api/dashboard/total-expense")
    public ResponseEntity<?> getTotalExpense(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Long userId = userDetails.getId();
            return ResponseEntity.ok(transactionService.getTotalExpenseByUser(userId));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/api/dashboard/balance")
    public ResponseEntity<?> getBalance(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Long userId = userDetails.getId();
            return ResponseEntity.ok(transactionService.getBalanceByUser(userId));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /*
     * Global summary (/api/dashboard/summary) has been removed as per 
     * the new security requirements: "global summary should not be visible at all".
     */
}
