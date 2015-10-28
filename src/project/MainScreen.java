package project;


import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Makes the main and first screen showed.
 * @author  Clifton West, John Burrell
 * @version October 3, 2015
 */
public class MainScreen extends Application {

    /** A static variable containing the current stage */
    private static Stage stage;

    /**
     * Initializes the Main screen.
     * @param primaryStage  The current Stage.
     * @throws Exception
     */
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
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("");
            primaryStage.show();
        } catch(IOException ioe) {
            System.out.println("Error In Showing the Main Screen.");
        }
    }

    /**
     * Returns the current stage.
     * @return stage    The current stage.
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Entry point into the program.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
