package com.example.sqlmusic_ui;

import com.example.sqlmusic_ui.model.Album;
import com.example.sqlmusic_ui.model.Artist;
import com.example.sqlmusic_ui.model.Datasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class MainController {
    @FXML
    public TableView<Album> albumsByArtistTable;
    @FXML
    public Button listArtistsButton;
    @FXML
    public Button showAlbumsForArtistButton;
    @FXML
    public Button updateArtistsbutton;
    @FXML
    private TableView<Artist> artistTable;

    public void initialize() {
        System.out.println("Initializing main controller...");

        artistTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, artist, t1) -> {
            System.out.println("Cell clicked");
            showAlbumsForArtistButton.setDisable(false);
            updateArtistsbutton.setDisable(false);
        }));

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

        toggleTableVisibility(albumsByArtistTable, true, 1000, 1000);
        toggleTableVisibility(artistTable, false, 0, 0);
        listArtistsButton.setDisable(false);
        showAlbumsForArtistButton.setDisable(true);
    }

    private void toggleTableVisibility(TableView tableView, boolean isVisible, double preferredWidth, double preferredHeight ) {
        tableView.setVisible(isVisible);
        tableView.setPrefWidth(preferredWidth);
        tableView.setPrefHeight(preferredHeight);
    }

    public void handleListArtists(ActionEvent actionEvent) {
        System.out.println("click list artists");
        toggleTableVisibility(artistTable, true, 1000, 1000);
        toggleTableVisibility(albumsByArtistTable, false, 0, 0);
        listArtistsButton.setDisable(true);
        showAlbumsForArtistButton.setDisable(false);
    }

    public void handleShowAlbumsForArtist(ActionEvent actionEvent) {
        System.out.println("click show albums");
        Artist selectedArtist = artistTable.getSelectionModel().getSelectedItem();
        if (selectedArtist == null) return;
        listAlbumsByArtistId(selectedArtist.getId());

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