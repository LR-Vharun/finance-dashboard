package com.example.finaceDashboard.service;

import com.example.finaceDashboard.dto.AdminUserDetailDto;
import com.example.finaceDashboard.dto.DashboardSummaryDto;
import com.example.finaceDashboard.entity.Transaction;
import com.example.finaceDashboard.entity.TransactionType;
import com.example.finaceDashboard.entity.User;
import com.example.finaceDashboard.repository.TransactionRepository;
import com.example.finaceDashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    // ── Create transaction scoped to the calling user ──────────────
    public Transaction createTransaction(Transaction transaction, Long userId) {
        if (transaction.getAmount() == null || transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (transaction.getDate() == null) {
            transaction.setDate(LocalDate.now());
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        transaction.setUser(user);
        return transactionRepository.save(transaction);
    }

    // ── Get transactions for a specific user ────────────────────────
    public List<Transaction> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    // Legacy alias kept for backward compatibility
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    // ── Per-user aggregates ─────────────────────────────────────────
    public Double getTotalIncomeByUser(Long userId) {
        return transactionRepository.findByUserId(userId).stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public Double getTotalExpenseByUser(Long userId) {
        return transactionRepository.findByUserId(userId).stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public Double getBalanceByUser(Long userId) {
        return getTotalIncomeByUser(userId) - getTotalExpenseByUser(userId);
    }

    // ── Legacy (used by old dashboard endpoints) ────────────────────
    public Double getTotalIncome() {
        return getTotalIncomeByUser(1L);
    }

    public Double getTotalExpense() {
        return getTotalExpenseByUser(1L);
    }

    public Double getBalance() {
        return getBalanceByUser(1L);
    }

    // ── Per-user summary DTO ────────────────────────────────────────
    public DashboardSummaryDto getSummaryByUser(Long userId) {
        Double income  = getTotalIncomeByUser(userId);
        Double expense = getTotalExpenseByUser(userId);
        return new DashboardSummaryDto(income, expense, income - expense);
    }

    // ── Global summary (USER_PRO + ADMIN) ─────────────────────────
    public DashboardSummaryDto getGlobalSummary() {
        List<Transaction> all = transactionRepository.findAll();
        double income  = all.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount).sum();
        double expense = all.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount).sum();
        return new DashboardSummaryDto(income, expense, income - expense);
    }

    // ── Admin: full detail for a single user ───────────────────────
    public AdminUserDetailDto getUserDetail(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        List<Transaction> txns = transactionRepository.findByUserId(userId);
        double income  = txns.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount).sum();
        double expense = txns.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount).sum();
        return new AdminUserDetailDto(
                user.getId(), user.getEmail(), user.getRole().name(),
                txns, income, expense, income - expense);
    }
}
