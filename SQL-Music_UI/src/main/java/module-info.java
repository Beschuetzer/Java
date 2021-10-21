module com.example.sqlmusic_ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.junit.jupiter.api;


    opens com.example.sqlmusic_ui to javafx.fxml;
    exports com.example.sqlmusic_ui;
}