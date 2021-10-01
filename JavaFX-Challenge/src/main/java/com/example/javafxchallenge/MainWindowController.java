package com.example.javafxchallenge;

import com.example.javafxchallenge.Contacts.Contact;
import com.example.javafxchallenge.Contacts.ContactData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class MainWindowController {
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TableView<Contact> tableView;

    public void initialize() {
        System.out.println("Starting Controller");
        tableView.setItems(ContactData.getInstance().getContacts());
    }

    @FXML
    public void handleNew() {
        //Initialize Dialog Window
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Contact");

        //Load FXML into Dialog
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        //Add Buttons to DialogPane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        //Handle Response
        Optional<ButtonType> response = dialog.showAndWait();
        if (!response.isPresent()) return;
        if (response.get() == ButtonType.OK) {
            //Get the controller for the loaded fxml file above
            NewDialogController controller = fxmlLoader.getController();

            //Call a public method in the controller to handle the adding
            controller.processResults();
        } else {
            dialog.close();
        }
    }
    @FXML
    public void handleEdit() {

    }
    @FXML
    public void handleDelete() {

    }
}