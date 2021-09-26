module com.example.javafxcontrols {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxcontrols to javafx.fxml;
    exports com.example.javafxcontrols;
}