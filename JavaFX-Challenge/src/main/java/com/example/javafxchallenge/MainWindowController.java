package com.example.javafxchallenge;

import com.example.javafxchallenge.dataModel.Contact;
import com.example.javafxchallenge.dataModel.ContactData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MainWindowController {
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TableView<Contact> tableView;

    private ContextMenu contactsContextMenu;

    public void initialize() {
        System.out.println("Starting Controller");
        ObservableList<Contact> contacts = ContactData.getInstance().getContacts();
        tableView.setItems(contacts);
        setupContextMenu();
    }

    @FXML
    public void handleNew() {
        Map dialogAndLoader = createAddDialog(null);
        FXMLLoader fxmlLoader = (FXMLLoader) dialogAndLoader.get("loader");
        Dialog<ButtonType> dialog = (Dialog<ButtonType>) dialogAndLoader.get("dialog");
        Optional<ButtonType> response = (Optional<ButtonType>) dialogAndLoader.get("response");

        //Handle Response
        handleResponse(response, fxmlLoader, dialog);
    }

    @FXML
    public void handleEdit() {

    }

    @FXML
    public void handleDelete() {

    }

    public void confirmDeleteItem(Contact contactToDelete) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete Contact: " + contactToDelete.toString());
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();

        if (!result.isPresent()) return;
        if (result.get() == ButtonType.OK) {
            ContactData.getInstance().deleteContact(contactToDelete);
        } else {
            alert.close();
        }
    }

    public void handleTableViewKeyPress(KeyEvent keyEvent) {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact == null) return;

        if (keyEvent.getCode().equals(KeyCode.DELETE)) confirmDeleteItem(selectedContact);
    }

    private Map<String, Object> createAddDialog(Contact contactToLoad) {
        //Initialize Dialog Window
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());

        //Load FXML into Dialog
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }

        //Add Buttons to DialogPane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        if (contactToLoad != null) {
            dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
            dialog.setTitle("Edit Contact");
            NewDialogController controller = fxmlLoader.getController();
            controller.populateFields(contactToLoad);
        } else {
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.setTitle("Add New Contact");
        }

        Map<String, Object> toReturn = new HashMap<>();
        Optional<ButtonType> response = dialog.showAndWait();
        toReturn.put("loader", fxmlLoader);
        toReturn.put("dialog", dialog);
        toReturn.put("response", response);

        return toReturn;
    }

    private void handleResponse(Optional<ButtonType> response, FXMLLoader fxmlLoader, Dialog<ButtonType> dialog) {
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

    private void setupContextMenu() {
        contactsContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        MenuItem editMenuItem = new MenuItem("Edit");

        deleteMenuItem.setOnAction((actionEvent) -> {
            Contact selectedItem = tableView.getSelectionModel().getSelectedItem();
            confirmDeleteItem(selectedItem);
        });

        editMenuItem.setOnAction((actionEvent) -> {
            Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
            Map dialogAndLoader = createAddDialog(selectedContact);
            FXMLLoader fxmlLoader = (FXMLLoader) dialogAndLoader.get("loader");
            Dialog<ButtonType> dialog = (Dialog<ButtonType>) dialogAndLoader.get("dialog");
            Optional<ButtonType> response = (Optional<ButtonType>) dialogAndLoader.get("response");

            if (!response.isPresent()) return;
            if (response.get() == ButtonType.APPLY) {
                NewDialogController controller = fxmlLoader.getController();
                controller.processResults();
                ContactData.getInstance().deleteContact(selectedContact);
            } else {
                dialog.close();
            }
        });

        contactsContextMenu.getItems().addAll(deleteMenuItem, editMenuItem);
        tableView.setContextMenu(contactsContextMenu);
    }
}