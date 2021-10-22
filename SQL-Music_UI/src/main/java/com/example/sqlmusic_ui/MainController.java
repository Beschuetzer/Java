package com.example.sqlmusic_ui;

import com.example.sqlmusic_ui.model.Album;
import com.example.sqlmusic_ui.model.Artist;
import com.example.sqlmusic_ui.model.Datasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class MainController {
    @FXML
    public TableView<Album> albumsByArtistTable;
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

    public void listAlbumsByArtistId(Integer artistId) {
        final Artist artist = (Artist) artistTable.getSelectionModel().getSelectedItem();
        if (artist == null) {
            System.out.println("No artist selected");
            return;
        }

        Task<ObservableList<Album>> task = new GetAlbumsByArtistIdTask(artistId);
        albumsByArtistTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    public void handleListArtists(ActionEvent actionEvent) {
        System.out.println("click list artists");
    }

    public void handleShowAlbumsForArtist(ActionEvent actionEvent) {
        System.out.println("click show albums");

    }

    public void handleUpdateArtists(ActionEvent actionEvent) {
        System.out.println("click update artists");

    }

    //REMEMBER JAVAFX REQUIRES ALL NON-UI RELATED CODE TO BE RUN ON A SEPARATE THREAD FROM MAIN THREAD
    //THE VALUE RETURNED BY Task.call() IS THE .valueProperty (bindable)
    class GetAllArtistsTask extends Task {
        @Override
        protected ObservableList<Artist> call() {
            return FXCollections.observableArrayList(
                    Datasource.getInstance().queryArtists()
            );
        }
    }

    class GetAlbumsByArtistIdTask extends Task {
        private Integer artistId;

        public GetAlbumsByArtistIdTask(Integer artistId) {
            this.artistId = artistId;
        }

        @Override
        protected ObservableList<Album> call() throws Exception {
            return FXCollections.observableArrayList(
                    Datasource.getInstance().getAlbums(this.artistId)
            );
        }
    }
}