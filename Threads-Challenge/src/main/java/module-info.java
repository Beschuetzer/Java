module com.example.threadschallenge {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.threadschallenge to javafx.fxml;
    exports com.example.threadschallenge;
}