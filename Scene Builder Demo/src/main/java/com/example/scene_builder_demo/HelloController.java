package com.example.scene_builder_demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Button okButton;

    @FXML
    private Label okButtonLabel;

    @FXML
    public void handleOkClick() {
        okButtonLabel.setText("Ok Button Pressed");
    }
}