package major.adam.javafx_demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene(scene);

//        GridPane root = new GridPane();
//        root.setAlignment(Pos.CENTER);
//        root.setVgap(100);
//        root.setHgap(10);
//        Label label = new Label("Welcome to JavaFx!");
//        label.setTextFill(Color.GREEN);
//        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 32));
//        root.getChildren().add(label);
//        stage.setScene(new Scene(root, 300, 290));

        stage.setTitle("Hello!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}