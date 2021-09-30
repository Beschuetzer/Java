module com.example.scene_builder_demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.scene_builder_demo to javafx.fxml;
    exports com.example.scene_builder_demo;
}