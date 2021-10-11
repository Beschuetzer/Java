module com.example.javafxconcurrency {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxconcurrency to javafx.fxml;
    exports com.example.javafxconcurrency;
}