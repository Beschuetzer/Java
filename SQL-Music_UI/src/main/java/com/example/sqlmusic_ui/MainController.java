package com.example.sqlmusic_ui;

import com.example.sqlmusic_ui.model.Artist;
import com.example.sqlmusic_ui.model.Datasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class MainController {
    @FXML
    private TableView<Artist> artistTable;

    public void initialize() {
        System.out.println("Initializing main controller...");
    }

    public void listArtists() {
        Task<ObservableList<Artist>> task = new GetAllArtistsTask();
        artistTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    class GetAllArtistsTask extends Task {
        @Override
        protected ObservableList<Artist> call() {
            return FXCollections.observableArrayList(
                    Datasource.getInstance().queryArtists()
            );
        }
    }
}