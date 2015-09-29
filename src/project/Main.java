package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
            //Removes the stage buttons
            //primaryStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add("/fxml/styles/Main.css");

            primaryStage.setScene(scene);

            primaryStage.setTitle("Hello World");
            //Maximizes the window
            //primaryStage.setMaximized(true);

            primaryStage.setFullScreen(true);
            primaryStage.show();
        } catch (IOException ioe) {
            System.err.println("File not found");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}