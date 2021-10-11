package com.example.javafxconcurrency;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FetchListService extends Service<ObservableList<String>> {
    private IntegerProperty argumentPassed = new SimpleIntegerProperty(this, "No Argument Received");

    public final void setArgumentPassed(Integer newValue) {
        this.argumentPassed.set(newValue);
    }
    public final Integer getArgumentPassed() {
        return argumentPassed.get();
    }
    public final IntegerProperty argumentPassedProperty() {
        return argumentPassed;
    }


    //This is run when service.start() is called;
    @Override
    protected Task<ObservableList<String>> createTask() {
        //get the arguments passed here (only 1 in this case)
        Integer _argumentPassed = getArgumentPassed();

        return new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws Exception {
                System.out.println("task running......");

                System.out.println(_argumentPassed);

//               URI uri = new URI("https://icanhazdadjoke.com/");
                String[] names = {
                        "Timmy",
                        "Adam",
                        "John",
                        "Jeff",
                        "Jim"
                };

                ObservableList<String> employees = FXCollections.observableArrayList();

                for (int i = 0; i < names.length; i++) {
                    employees.add(names[i]);
                    updateProgress(i + 1, names.length);
                    updateMessage("Fetching '" + names[i] + "'");
                    Thread.sleep(200);
                }

                //Platform.runLater() runs the code on the JavaFx app thread
                //even if it is inside of another thread
                //this would update UI but not recommended because it merges Model and View
                //preferred way is to use data-binding
//               Platform.runLater(() -> {
//                       listView.setItems(employees);
//               });

                return employees;
            }

        };
    }
}
