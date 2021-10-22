module com.example.sqlmusic_ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.junit.jupiter.api;

    opens com.example.sqlmusic_ui.model to javafx.base;
    opens com.example.sqlmusic_ui to javafx.fxml;
    exports com.example.sqlmusic_ui;
    exports com.example.sqlmusic_ui.custom;
}