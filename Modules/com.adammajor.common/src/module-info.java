module com.adammajor.common {
    requires javafx.base;

    exports com.adammajor.common to javafx.fxml, com.adammajor.db, com.adammajor.ui;
    opens com.adammajor.common to javafx.fxml, javafx.base;
}