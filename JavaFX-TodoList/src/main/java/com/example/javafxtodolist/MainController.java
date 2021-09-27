package com.example.javafxtodolist;

import com.example.javafxtodolist.dataModel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea todoListDetails;
    @FXML
    private Label dueDateLabel;

    private final List<TodoItem> todoItems = new ArrayList<>();

    public void initialize() {
        TodoItem todoItem1 = new TodoItem("Mail Birthday Card", "Buy a card", LocalDate.of(2021, Month.SEPTEMBER, 30));
        TodoItem todoItem2 = new TodoItem("Doctor's Appointment", "See Dr. Smith at 123. main st;  bring paperwork", LocalDate.of(2021, Month.OCTOBER, 10));
        TodoItem todoItem3 = new TodoItem("Finish Design Proposal", "I promised mike i'd email him this", LocalDate.of(2021, Month.OCTOBER, 20));
        TodoItem todoItem4 = new TodoItem("Pick up Doug", "Doug likes driving in cars", LocalDate.of(2021, Month.DECEMBER, 23));
        TodoItem todoItem5 = new TodoItem("Pick up dry cleaning", "Should be ready at 4pm but call first", LocalDate.of(2021, Month.SEPTEMBER, 23));

        this.todoItems.add(todoItem1);
        this.todoItems.add(todoItem2);
        this.todoItems.add(todoItem3);
        this.todoItems.add(todoItem4);
        this.todoItems.add(todoItem5);

        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> handleListViewChange(newValue));

        todoListView.getItems().setAll(todoItems);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
    }

    private void handleListViewChange(TodoItem newValue) {
        if (newValue != null) {
            TodoItem item = todoListView.getSelectionModel().getSelectedItem();
            todoListDetails.setText(item.getDetails());
            DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/d/yyyy");
            dueDateLabel.setText(df.format(item.getDeadline()));
        }
    }
}