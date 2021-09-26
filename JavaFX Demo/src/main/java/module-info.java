module major.adam.javafx_demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens major.adam.javafx_demo to javafx.fxml;
    exports major.adam.javafx_demo;
}