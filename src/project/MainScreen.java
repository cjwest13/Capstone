package project;
/**
 * Created by cj on 10/3/2015.
 */

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainScreen extends Application {
    /**
    static String screen1 = "main";
    static String screen1fxml = "/fxml/Main.fxml";
    static String screen2 = "screen2";
    static String screen2fxml = "/fxml/Screen2.fxml";
    static String screen3 = "settings";
    static String screen3fxml = "/fxml/Settings.fxml";
    */


    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));

            FadeTransition ft = new FadeTransition(Duration.millis(3000), root);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add("/styles/main.css");
            primaryStage.setScene(scene);
            //primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("");
            primaryStage.show();
        } catch(IOException ioe) {
            System.out.println("gahhh");

        }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
