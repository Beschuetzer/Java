package com.example.sqlmusic_ui;

import com.example.sqlmusic_ui.model.Album;
import com.example.sqlmusic_ui.model.Artist;
import com.example.sqlmusic_ui.model.Datasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class MainController {
    private Artist selectedArtist;

    @FXML
    public TableView<Album> albumsByArtistTable;
    @FXML
    public Button listArtistsButton;
    @FXML
    public Button showAlbumsForArtistButton;
    @FXML
    public Button updateArtistsButton;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public BorderPane mainBorderPane;
    @FXML
    private TableView<Artist> artistTable;

    public void initialize() {
        System.out.println("Initializing main controller...");

        artistTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, artist, t1) -> {
            System.out.println("Cell clicked");
            showAlbumsForArtistButton.setDisable(false);
            updateArtistsButton.setDisable(false);
        }));

    }

    public void listArtists() {
        Task<ObservableList<Artist>> task = new GetAllArtistsTask();
        artistTable.itemsProperty().bind(task.valueProperty());
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(e -> progressBar.setVisible(false));
        task.setOnFailed(e -> progressBar.setVisible(false));
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

    @FXML
    public void handleListArtists(ActionEvent actionEvent) {
        System.out.println("click list artists");
        toggleTableVisibility(artistTable, true, 1000, 1000);
        toggleTableVisibility(albumsByArtistTable, false, 0, 0);
        listArtistsButton.setDisable(true);
        showAlbumsForArtistButton.setDisable(false);
    }

    @FXML
    public void handleShowAlbumsForArtist(ActionEvent actionEvent) {
        System.out.println("click show albums");
        Artist selectedArtist = artistTable.getSelectionModel().getSelectedItem();
        if (selectedArtist == null) return;
        listAlbumsByArtistId(selectedArtist.getId());

    }

    @FXML
    public void handleUpdateArtists(ActionEvent actionEvent) {
        System.out.println("click update artists");

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Modify Song:");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainWindow.class.getResource("modifySongDialogPane.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        //generally want show and wait rather than just show
        ModifySongDialogPaneController controller = fxmlLoader.getController();
        selectedArtist = artistTable.getSelectionModel().getSelectedItem();
        int selectedArtistIndex = artistTable.getSelectionModel().getSelectedIndex();

        //using a public method to pass data directly to ModifySongDialogPaneController
        controller.initData(selectedArtist);
        Optional<ButtonType> result = dialog.showAndWait();

        if (!result.isPresent()) return;
        if (result.get() == ButtonType.OK) {
            Artist newArtist = controller.processResults();
            UpdateArtistNameTask updateArtistNameTask = new UpdateArtistNameTask();
            System.out.println(artistTable.getItems());
            updateArtistNameTask.setOnSucceeded(e -> {
                Boolean taskResult = (Boolean) updateArtistNameTask.getValue();
                if (taskResult) {
                    System.out.println("Artist name updated successfully!");

                    //Since TableView artistsTable is bound to the values of artistList and selectedArtist is part of artistList, any changes are automatically observed/updated
                    selectedArtist.setName(newArtist.getName());

                    //there is a bug in older version of JavaFX causing update not to happen in some cases
                    //this fixes that (may not be needed on new versions of JavaFX)
                    artistTable.refresh();

                } else {
                    System.out.println("Unable to update artist name.  Try again.");
                }
            });
            updateArtistNameTask.setOnFailed(e -> System.out.println("Unable to update artist name.  Try again."));
            new Thread(updateArtistNameTask).start();

        } else if (result.get() == ButtonType.CANCEL){
            dialog.close();
        }
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

    class UpdateArtistNameTask extends Task {
        @Override
        protected Boolean call() throws Exception {
            return Datasource.getInstance().updateArtistName(selectedArtist);
        }
    }
}