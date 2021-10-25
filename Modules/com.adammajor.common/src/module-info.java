module com.adammajor {
    requires javafx.base;
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    requires javafx.graphics;

    exports com.adammajor.common to javafx.fxml;
    exports com.adammajor.ui to javafx.graphics, javafx.fxml;

    opens com.adammajor.common to javafx.fxml, javafx.base;
    opens com.adammajor.ui to javafx.fxml, javafx.graphics;
}