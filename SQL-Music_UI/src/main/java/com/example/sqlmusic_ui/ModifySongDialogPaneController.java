package com.example.sqlmusic_ui;

import com.example.sqlmusic_ui.model.Artist;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModifySongDialogPaneController {
    private Artist selectedArtist;

    @FXML
    public TextField desiredNameTextField;
    @FXML
    public Label currentNameLabel;

    public void initialize() {
        System.out.println("Initializing ModifySongDialogPaneController controller...");
    }

    public void initData(Artist selectedArtist) {
        this.selectedArtist = selectedArtist;
        currentNameLabel.setText(selectedArtist.getName());
    }

    public Artist processResults() {
        return new Artist(desiredNameTextField.getText(), selectedArtist.getId());
    }
}

