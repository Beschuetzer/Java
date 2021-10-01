package com.example.javafxchallenge;

import com.example.javafxchallenge.Contacts.Contact;
import com.example.javafxchallenge.Contacts.ContactData;
import javafx.beans.property.SimpleStringProperty;
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
        Boolean firstNameValid = !firstNameField.getText().isEmpty() && !firstNameField.getText().isBlank();
        Boolean lastNameValid = !lastNameField.getText().isEmpty() && !lastNameField.getText().isBlank();
        Boolean phoneNumberValid = !phoneNumberField.getText().isEmpty() && !phoneNumberField.getText().isBlank();
        Boolean notesValid = !notesField.getText().isEmpty() && !notesField.getText().isBlank();
        if (!firstNameValid || !lastNameValid || !phoneNumberValid || !notesValid) return null;

        //Create a new Contact and add it to the ContactData contacts list
        SimpleStringProperty firstName = new SimpleStringProperty(firstNameField.getText());
        SimpleStringProperty lastName = new SimpleStringProperty(lastNameField.getText());
        SimpleStringProperty phoneNumber = new SimpleStringProperty(phoneNumberField.getText());
        SimpleStringProperty notes = new SimpleStringProperty(notesField.getText());
        Contact newContact = new Contact(firstName, lastName, phoneNumber, notes);

        ContactData.getInstance().addContact(newContact);

        //return new Contact
        return  newContact;
    }
}
