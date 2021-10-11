package com.example.threadschallenge;

public class BankAccount {
    private double balance;
    private String accountNumber;
    private static int lastUsedAccountNumber = 0;

    public BankAccount(double balance) {
        this.balance = balance;

        //generating new accountNumber
        Integer nextAccountNumber = ++lastUsedAccountNumber;
        this.accountNumber = String.format("%12s", nextAccountNumber).replace(" ", "0");
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }
}
