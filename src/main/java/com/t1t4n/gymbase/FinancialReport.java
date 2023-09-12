package com.t1t4n.gymbase;

public class FinancialReport {
    int incomeTotal;
    double expensesTotal;
    double estimatedProfit;
    String date;

    public FinancialReport(int incomeTotal, double expensesTotal, String date) {
        this.incomeTotal = incomeTotal;
        this.expensesTotal = expensesTotal;
        this.date = date;
    }

    public int getIncomeTotal() {
        return incomeTotal;
    }

    public void setIncomeTotal(int incomeTotal) {
        this.incomeTotal = incomeTotal;
    }

    public double getExpensesTotal() {
        return expensesTotal;
    }

    public void setExpensesTotal(double expensesTotal) {
        this.expensesTotal = expensesTotal;
    }

    public double getEstimatedProfit() {
        return incomeTotal - expensesTotal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
