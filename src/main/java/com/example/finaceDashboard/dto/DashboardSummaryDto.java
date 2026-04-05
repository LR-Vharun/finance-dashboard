package com.example.finaceDashboard.dto;

public class DashboardSummaryDto {

    private Double totalIncome;
    private Double totalExpense;
    private Double balance;

    public DashboardSummaryDto() {}

    public DashboardSummaryDto(Double totalIncome, Double totalExpense, Double balance) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
