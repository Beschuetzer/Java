module com.example.javafxchallenge {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens com.example.javafxchallenge to javafx.fxml;
    opens com.example.javafxchallenge.dataModel to javafx.base;
    exports com.example.javafxchallenge;
}