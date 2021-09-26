module com.example.javafxevents {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxevents to javafx.fxml;
    exports com.example.javafxevents;
}