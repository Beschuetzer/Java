package com.example.javafxevents;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    private Runnable updateLabel = new Runnable() {
        @Override
        public void run() {
            String thread = Platform.isFxApplicationThread() ? "UI thread" : "background thread";
            System.out.println("Updating on " + thread);
            label.setText("Background thread finished...");
        }
    };

    @FXML
    private TextField textField;
    @FXML
    private Button helloButton;
    @FXML
    private Button byeButton;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Label label;

    @FXML
    public void initialize() {
        helloButton.setDisable(true);
        byeButton.setDisable(true);
    }

    @FXML
    public void onButtonClick(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(helloButton)) {
                System.out.println("Hello, " + textField.getText() + "!");
        } else if (source.equals(byeButton)) {
                System.out.println("Bye, " + textField.getText() + "!");
        }

        if (checkBox.isSelected()) {
            textField.clear();
            helloButton.setDisable(true);
            byeButton.setDisable(true);
        }

        //This creates another thread
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    String thread = Platform.isFxApplicationThread() ? "UI thread" : "background thread";
                    System.out.println("Sleeping on " + thread);

                    Thread.sleep(3000);
                    Platform.runLater(updateLabel);
                } catch (InterruptedException exception) {
                    System.out.println(exception);
                }
            }
        };

        new Thread(task).start();
    }

    @FXML
    public void onHandleKeyReleased() {
        String text = textField.getText();
        boolean shouldDisableButtons = text.isEmpty() || text.trim().isEmpty();
        helloButton.setDisable(shouldDisableButtons);
        byeButton.setDisable(shouldDisableButtons);
    }

    @FXML
    public void handleCheckBoxAction(ActionEvent e) {
        if (checkBox.isSelected()) {
            System.out.println("Checkbox is checked");
        } else System.out.println("CHeckbox is not checked");
    }
}