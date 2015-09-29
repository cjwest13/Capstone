package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        //Removes the stage buttons
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add("/fxml/styles/Main.css");

        primaryStage.setScene(scene);

        primaryStage.setTitle("Hello World");
        //Maximizes the window
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}