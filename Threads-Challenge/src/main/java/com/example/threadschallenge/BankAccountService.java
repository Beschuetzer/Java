package com.example.threadschallenge;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class BankAccountService extends Service {
    private BankAccount bankAccount;
    private DoubleProperty amount = new SimpleDoubleProperty();

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public Double getAmount() {
        return this.amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public BankAccountService(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    protected Task createTask() {
        Double _amountToAdd = getAmount();
        if (_amountToAdd == 0) return null;

        return new Task() {
            @Override
            protected Object call() throws Exception {
                if (_amountToAdd < 0) bankAccount.withdraw(Math.abs(_amountToAdd));
                else bankAccount.deposit(_amountToAdd);

                Double newBalance = bankAccount.getBalance();
//                updateValue(newBalance);

                System.out.println("newBalance = " + newBalance);
                return _amountToAdd;
            }
        };
    }
}
