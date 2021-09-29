package com.example.javafxtodolist;

import com.example.javafxtodolist.dataModel.TodoData;
import com.example.javafxtodolist.dataModel.TodoItem;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class MainController {
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea todoListDetails;
    @FXML
    private Label dueDateLabel;
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private ToggleButton filterToggleButton;

    private List<TodoItem> todoItems = new ArrayList<>();
    private FilteredList<TodoItem> filteredList;
    private Predicate<TodoItem> wantAllItems = new Predicate<TodoItem>() {
        @Override
        public boolean test(TodoItem todoItem) {
            return true;
        }
    };
    private Predicate<TodoItem> wantTodaysITems = new Predicate<TodoItem>() {
        @Override
        public boolean test(TodoItem todoItem) {
            LocalDate today = LocalDate.now();
            if (todoItem.getDeadline().isEqual(today)) return true;
            return false;
        }
    };

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

        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
                confirmDeleteItem(selectedItem);
            }
        });
        listContextMenu.getItems().addAll(deleteMenuItem);

        filteredList = new FilteredList<TodoItem>(TodoData.getInstance().getTodoItems(), (todoItem -> {
            return true;
        }));
        SortedList<TodoItem> sortedList = new SortedList<TodoItem>(filteredList, (item1, item2) -> {
                return item1.getDeadline().compareTo(item2.getDeadline());
        });
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleListViewChange(newValue));
        todoListView.setItems(sortedList);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();

        //THIS CHANGES THE STYLING OF ITEMS (CELLS) IN THE LISTVIEW ON THE LEFT
        todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> todoItemListView) {
                ListCell<TodoItem> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(TodoItem todoItem, boolean b) {
                        super.updateItem(todoItem, b);
                        if (isEmpty()) setText(null);
                        else {
                            setText(todoItem.getShortDescription());
                            Color colorToSet = Color.BLACK;
                            LocalDate now = LocalDate.now();
                            if (todoItem.getDeadline().isBefore(now)) colorToSet = Color.RED;
                            else if (todoItem.getDeadline().equals(now)) colorToSet = Color.ORANGERED;
                            else if (todoItem.getDeadline().equals(now.plusDays(1))) colorToSet = Color.ORANGE;
                            setTextFill(colorToSet);

                        }
                    }
                };
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(listContextMenu);
                    }
                });
                return cell;
            }
        });
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

        if (!result.isPresent()) return;
        if (result.get() == ButtonType.OK) {
            TodoItemDialogueController controller = fxmlLoader.getController();
            TodoItem newItem = controller.processResults();
            todoListView.getSelectionModel().select(newItem);
        } else if (result.get() == ButtonType.CANCEL){
            dialog.close();
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

    @FXML
    public void handleListViewKeyPress(KeyEvent event) {
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;

        if (event.getCode().equals(KeyCode.DELETE)) {
            confirmDeleteItem(selectedItem);
        }
    }

    @FXML
    public void handleFilterButton(ActionEvent event) {
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();

        if (filterToggleButton.isSelected()) {
            filteredList.setPredicate(wantTodaysITems);

            if (filteredList.isEmpty()) {
                todoListDetails.clear();
                dueDateLabel.setText("");
            } else if (filteredList.contains(selectedItem)){
                todoListView.getSelectionModel().select(selectedItem);
            } else {
                todoListView.getSelectionModel().selectFirst();
            }
        } else {
            filteredList.setPredicate(wantAllItems);
            todoListView.getSelectionModel().select(selectedItem);
        }

    }

    @FXML
    public void handleExit() {
        Platform.exit();
    }

    public void confirmDeleteItem(TodoItem itemToDelete) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Todo Item");
        alert.setHeaderText("Delet Item: " + itemToDelete.getShortDescription());
        alert.setContentText("Are you sure?  Press OK to confirm or CANCEL to back out.");
        Optional<ButtonType> result = alert.showAndWait();

        if (!result.isPresent()) return;
        if (result.get() == ButtonType.OK) {
            TodoData.getInstance().deleteTodoItem(itemToDelete);
        } else {
            alert.close();
        }
    }
}