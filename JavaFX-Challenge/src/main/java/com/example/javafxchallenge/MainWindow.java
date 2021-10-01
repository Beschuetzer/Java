package com.example.javafxchallenge;

import com.example.javafxchallenge.dataModel.ContactData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {

    @Override
    public void init() throws Exception {
        try {
            ContactData.getInstance().loadContacts();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Init error");
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("mainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("My Contacts");
        stage.getIcons().add(new Image("/toolbarButtonGraphics/development/Application16.gif"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        ContactData.getInstance().saveContacts();
    }

    public static void main(String[] args) {
        launch();
    }
}