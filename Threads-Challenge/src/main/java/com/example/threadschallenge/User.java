package com.example.threadschallenge;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<BankAccount> bankAccounts = new ArrayList<BankAccount>();

    public User(String name, BankAccount bankAccount) {
        this.name = name;
        addBankAccount(bankAccount);
    }

    public String getName() {
        return name;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public boolean deposit(double amount) {
        if (amount == 0 ) return false;

        this.bankAccounts.get(0).deposit(amount);
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount == 0 ) return false;

        this.bankAccounts.get(0).withdraw(amount);
        return true;
    }

    private boolean addBankAccount(BankAccount bankAccount) {
        if (bankAccounts != null && bankAccounts.contains(bankAccount)) return false;

        this.bankAccounts.add(bankAccount);
        return true;
    }
}
