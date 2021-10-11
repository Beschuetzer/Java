package com.example.threadschallenge;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    public Label currentValue;

    private BankAccount sharedAccount;

    public void initialize () {
        sharedAccount = new BankAccount(1000);
        User tom = new User("Tom", sharedAccount);
        User adam = new User("Adam", sharedAccount);
        System.out.println(sharedAccount.getAccountNumber());
        System.out.println("Adam's account #: " + adam.getBankAccounts().get(0).getAccountNumber());
        System.out.println("Toms's account #: " + tom.getBankAccounts().get(0).getAccountNumber());


    }

}