package com.example.sqlmusic_ui;

import com.example.sqlmusic_ui.model.Datasource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
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
        launch();
    }
}