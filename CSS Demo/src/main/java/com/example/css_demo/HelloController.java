package com.example.css_demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

public class HelloController {
    @FXML
    private Label label;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button chooseFile;
    @FXML
    private Button chooseDirectory;

    @FXML
    public void initialize() {

    }

    @FXML
    public void handleMouseEnter(MouseEvent mouseEvent) {
        mouseEvent.getTarget();
    }

    @FXML
    public void handleMouseExit(MouseEvent mouseEvent)
    {
    }

    @FXML
    public void handleButtonOneClick() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(gridPane.getScene().getWindow());
        if (file != null) System.out.println(file.getPath());
        else System.out.println("Cancelled");
    }

    public void handleButtonTwoClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(gridPane.getScene().getWindow());
        if (file != null) System.out.println(file.getPath());
        else System.out.println("Cancelled");
    }

    public void handleButtonThreeClick() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(gridPane.getScene().getWindow());
        if (file != null) System.out.println(file.getPath());
    }
}