module com.example.javafxtodolist {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxtodolist to javafx.fxml;
    exports com.example.javafxtodolist;
}