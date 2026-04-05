package com.example.finaceDashboard.dto;

import com.example.finaceDashboard.entity.Transaction;

import java.util.List;

public class AdminUserDetailDto {

    private Long userId;
    private String email;
    private String role;
    private List<Transaction> transactions;
    private Double totalIncome;
    private Double totalExpense;
    private Double balance;

    public AdminUserDetailDto() {}

    public AdminUserDetailDto(Long userId, String email, String role,
                              List<Transaction> transactions,
                              Double totalIncome, Double totalExpense, Double balance) {
        this.userId       = userId;
        this.email        = email;
        this.role         = role;
        this.transactions = transactions;
        this.totalIncome  = totalIncome;
        this.totalExpense = totalExpense;
        this.balance      = balance;
    }

    public Long getUserId()                        { return userId; }
    public void setUserId(Long userId)             { this.userId = userId; }
    public String getEmail()                       { return email; }
    public void setEmail(String email)             { this.email = email; }
    public String getRole()                        { return role; }
    public void setRole(String role)               { this.role = role; }
    public List<Transaction> getTransactions()     { return transactions; }
    public void setTransactions(List<Transaction> t){ this.transactions = t; }
    public Double getTotalIncome()                 { return totalIncome; }
    public void setTotalIncome(Double totalIncome) { this.totalIncome = totalIncome; }
    public Double getTotalExpense()                { return totalExpense; }
    public void setTotalExpense(Double v)          { this.totalExpense = v; }
    public Double getBalance()                     { return balance; }
    public void setBalance(Double balance)         { this.balance = balance; }
}
