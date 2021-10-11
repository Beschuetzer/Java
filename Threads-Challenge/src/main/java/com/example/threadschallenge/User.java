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

    private boolean addBankAccount(BankAccount bankAccount) {
        if (bankAccounts != null && bankAccounts.contains(bankAccount)) return false;

        this.bankAccounts.add(bankAccount);
        return true;
    }
}
