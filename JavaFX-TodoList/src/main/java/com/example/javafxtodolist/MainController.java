package com.example.javafxtodolist;

import com.example.javafxtodolist.dataModel.TodoData;
import com.example.javafxtodolist.dataModel.TodoItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea todoListDetails;
    @FXML
    private Label dueDateLabel;

    private List<TodoItem> todoItems = new ArrayList<>();

    public void initialize() {
//        TodoItem todoItem1 = new TodoItem("Mail Birthday Card", "Buy a card", LocalDate.of(2021, Month.SEPTEMBER, 30));
//        TodoItem todoItem2 = new TodoItem("Doctor's Appointment", "See Dr. Smith at 123. main st;  bring paperwork", LocalDate.of(2021, Month.OCTOBER, 10));
//        TodoItem todoItem3 = new TodoItem("Finish Design Proposal", "I promised mike i'd email him this", LocalDate.of(2021, Month.OCTOBER, 20));
//        TodoItem todoItem4 = new TodoItem("Pick up Doug", "Doug likes driving in cars", LocalDate.of(2021, Month.DECEMBER, 23));
//        TodoItem todoItem5 = new TodoItem("Pick up dry cleaning", "Should be ready at 4pm but call first", LocalDate.of(2021, Month.SEPTEMBER, 23));
//
//        this.todoItems.add(todoItem1);
//        this.todoItems.add(todoItem2);
//        this.todoItems.add(todoItem3);
//        this.todoItems.add(todoItem4);
//        this.todoItems.add(todoItem5);
//        TodoData.getInstance().setTodoItems(todoItems);

        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleListViewChange(newValue));
        todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Todo Item:");
        dialog.setHeaderText("Use this dialog to create a new todo item");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
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
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Ok Pressed");
            TodoItemDialogueController controller = fxmlLoader.getController();
            TodoItem newItem = controller.processResults();
            todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
            todoListView.getSelectionModel().select(newItem);
        } else {
            System.out.println("Something Else Pressed");
        }
    }

    public void handleListViewChange(TodoItem newValue) {
        if (newValue != null) {
            TodoItem item = todoListView.getSelectionModel().getSelectedItem();
            todoListDetails.setText(item.getDetails());
            DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/d/yyyy");
            dueDateLabel.setText(df.format(item.getDeadline()));
        }
    }
}