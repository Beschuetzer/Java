package com.example.css_demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

//import java.awt.*;

public class HelloController {
    @FXML
    private Label label;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button chooseFile;
    @FXML
    private Button chooseMultipleFile;
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
    public void handleOpenMultiple() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Multiple Files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("PDFs", "*.pdf")
        );
        List<File> files = fileChooser.showOpenMultipleDialog(gridPane.getScene().getWindow());

        if (files == null) return;
        files.forEach((filePath) -> {
            System.out.println(filePath);
        });
    }
    @FXML
    public void handleButtonOneClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick a file of Specific type");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("Zip", "*.zip"),
                new FileChooser.ExtensionFilter("Text", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.bmp", "*.gif", "*.png")
        );
        //Can use .showOpenMultipleDialog() to allow selecting multiple
        File file = fileChooser.showOpenDialog(gridPane.getScene().getWindow());
        if (file != null) System.out.println(file.getPath());
        else System.out.println("Cancelled");
    }
    @FXML
    public void handleButtonTwoClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(gridPane.getScene().getWindow());
        if (file != null) System.out.println(file.getPath());
        else System.out.println("Cancelled");
    }
    @FXML
    public void handleButtonThreeClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save App File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text", "*.txt"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        File file = fileChooser.showSaveDialog(gridPane.getScene().getWindow());
        if (file != null) System.out.println(file.getPath());
    }
    @FXML
    public void handleHyperLinkClick() {
        //To options to open webpage
        // (open system default browser
        System.out.println("Test");
//        try {
//            Desktop.getDesktop().browse(new URI("www.adammajor.com"));
//        } catch (Exception e) {
//            System.out.println(e);
//        }

        // or display contents in app using WebView control)
//        WebEngine engine = webViewControl.getEngine();
//        engine.load("www.adammajor.com");

        System.out.println("Clicked");
    }
}