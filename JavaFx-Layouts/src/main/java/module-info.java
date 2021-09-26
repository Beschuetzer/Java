module com.adamajor.javafxlayouts {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.adamajor.javafxlayouts to javafx.fxml;
    exports com.adamajor.javafxlayouts;
}