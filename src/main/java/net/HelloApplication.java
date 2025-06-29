package net;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/net/zine/supmtiproject/loginForm.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 603, 502);
        stage.setTitle("Task");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/net/zine/supmtiproject/images/team-building.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}