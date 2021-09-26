module major.adam.helloworldfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens major.adam.helloworldfx to javafx.fxml;
    exports major.adam.helloworldfx;
}