module com.example.css_demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.css_demo to javafx.fxml;
    exports com.example.css_demo;
}