package com.example.threadschallenge;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private static int lastUsedAccountNumber = 0;
    private final String accountNumber;
    private final ReentrantLock lock;
    private volatile double balance;

    public BankAccount(double balance) {
        lock = new ReentrantLock();
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

    public void printAccountNumber() {
        System.out.println("accountNumber = " + accountNumber);
    }

    //region synchronizing whole method
    public synchronized void deposit(double amount) {
        balance += amount;
    }

    public synchronized void withdraw(double amount) {
        balance -= amount;
    }
    //endregion

    //region synchronizing the critical section
    public void deposit2(double amount) {
        synchronized (this) {
            balance += amount;
        }
    }

    public void withdraw2(double amount) {
        synchronized (this) {
            balance -= amount;
        }
    }
    //endregion

    //region using lock  to synchronize
    public void deposit_lock(double amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public void withdraw_lock(double amount) {
        lock.lock();
        try {
            balance -= amount;
        } finally {
            lock.unlock();
        }
    }
    //endregion

    //region using lock.tryLock()  to synchronize
    public void deposit_try_lock(double amount) {
        boolean status = false;
        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    balance += amount;
                    status = true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Could not get the lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("status = " + status);
    }

    public void withdraw_try_lock(double amount) {
        boolean status = false;
        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    balance -= amount;
                    status = true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Could not get the lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("status = " + status);
    }
    //endregion
}
