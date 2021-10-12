package com.example.threadschallenge;

import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    public Label currentValue;

    private BankAccount sharedAccount;
    private Service<Integer> service;

    public void initialize () {
        sharedAccount = new BankAccount(1000);
        User tom = new User("Tom", sharedAccount);
        User adam = new User("Adam", sharedAccount);
        System.out.println(sharedAccount.getAccountNumber());
        System.out.println("Adam's account #: " + adam.getBankAccounts().get(0).getAccountNumber());
        System.out.println("Toms's account #: " + tom.getBankAccounts().get(0).getAccountNumber());

        new Thread(new Runnable() {
            @Override
            public void run() {
                tom.deposit(300);
                tom.withdraw(50);
                System.out.println("sharedAccount = " + sharedAccount.getBalance());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                adam.deposit(203.75);
                adam.withdraw(100);
                System.out.println("sharedAccount = " + sharedAccount.getBalance());
            }
        }).start();


//
//        BankAccountService bankAccountService = new BankAccountService(sharedAccount);
//        bankAccountService.setAmount(300);
//        bankAccountService.start();
//
//        System.out.println("sharedAccount balance = " + sharedAccount.getBalance());
//
//        bankAccountService.setAmount(-50);
//        bankAccountService.restart();
//
//        System.out.println("sharedAccount balance = " + sharedAccount.getBalance());
//
//        bankAccountService.setAmount(203.75);
//        bankAccountService.restart();
//
//        System.out.println("sharedAccount balance = " + sharedAccount.getBalance());
//
//        bankAccountService.setAmount(-100);
//        bankAccountService.restart();
//
//        System.out.println("sharedAccount balance = " + sharedAccount.getBalance());
//
//        currentValue.textProperty().bind(bankAccountService.valueProperty());
    }
}