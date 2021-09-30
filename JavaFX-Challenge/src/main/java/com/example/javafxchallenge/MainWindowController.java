package com.example.javafxchallenge;

import data.Contact;
import data.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class MainWindowController {
    @FXML
    private TableView<Contact> tableView;

    public void initialize() {
        System.out.println("Starting Controller");
        tableView.setItems(ContactData.getInstance().getContacts());
    }
}