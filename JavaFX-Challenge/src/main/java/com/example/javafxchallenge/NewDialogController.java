package com.example.javafxchallenge;

import com.example.javafxchallenge.Contacts.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextArea notesField;

    public Contact processResults() {
        //Need to verify fields are good

        //Create a new Contact and add it to the ContactData contacts list

        //return new Contact
    }
}
