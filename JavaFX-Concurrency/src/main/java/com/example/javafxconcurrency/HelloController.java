package com.example.javafxconcurrency;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

import java.util.Random;

public class HelloController {
    @FXML
    public Label progressLabel;
    @FXML
    public ProgressBar progressBar;
    @FXML
    private ListView listView;


    private static Random random = new Random();


    private Service<ObservableList<String>> someService;
    public void initialize() {
        System.out.println("Initializing HelloController");
        someService = new FetchListService();

        //RULE OF THUMB, SEE IF THERE IS A PROPERTY THAT CAN BE BOUND TO FOR CONTROLS BEFORE MANUALLY IMPLEMENTING

        //BINDING LISTVIEW'S ITEM PROPERTY TO SERVICE/TASK'S VALUE PROPERTY (SET ABOVE VIA updateValue()
        listView.itemsProperty().bind(someService.valueProperty());

        //BINDING THE TASKS PROGRESS PROPERTY TO THE PROGRESS BAR'S
        progressBar.progressProperty().bind(someService.progressProperty());

        //BINDING THE LABELS TEXT TO MESSAGE VIA service's messageProperty
        progressLabel.textProperty().bind(someService.messageProperty());
        progressBar.visibleProperty().bind(someService.runningProperty());
        progressLabel.visibleProperty().bind(someService.runningProperty());

        //setting up event handlers for the service
//       service.setOnRunning((event) -> {
//           toggleProgress(true);
//       });
//
//       service.setOnSucceeded((event) -> {
//           toggleProgress(false);
//       });


    }

    @FXML
    public void handleStartTask(ActionEvent actionEvent) {
        if (listView.getItems() != null) listView.getItems().clear();
//       service.restart();

        ((FetchListService) someService).setArgumentPassed(HelloController.random.nextInt());
        if (someService.getState() == Worker.State.SUCCEEDED || someService.getState() == Worker.State.CANCELLED) {
            someService.reset();
            someService.start();
        } else if (someService.getState() == Worker.State.READY) someService.start();
    }

    public void handleStopTask(ActionEvent actionEvent) {
        final Worker.State state = someService.getState();
        if (state == Worker.State.RUNNING || state == Worker.State.SCHEDULED) {
            someService.cancel();
        }
    }

    private void toggleProgress(boolean value) {
        progressBar.setVisible(value);
        progressLabel.setVisible(value);
    }
}