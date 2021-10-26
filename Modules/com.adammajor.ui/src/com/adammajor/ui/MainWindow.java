package com.adammajor.ui;

import com.adammajor.db.Datasource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/com/adammajor/ui/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Screen.getPrimary().getBounds().getWidth() / 2, Screen.getPrimary().getBounds().getHeight());

        //loading the artists on startup
        MainController mainController = fxmlLoader.getController();
        mainController.listArtists();
        mainController.listAlbumsByArtistId(30);

        stage.setTitle("SQL Music App!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        try {
            Datasource.getInstance().open();
        } catch (SQLException sqlException) {
            System.out.println("Unable to connect to Database;  need to implement UI message");
        }
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Closing database");
        Datasource.getInstance().close();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}