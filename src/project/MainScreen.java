package project;


import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.NextScreen;

import java.io.IOException;

/**
 * Makes the main and first screen showed.
 * @author  Clifton West, John Burrell
 * @version October 3, 2015
 */
public class MainScreen extends Application implements NextScreen {

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
        NextScreen.super.goToNextScreen("/fxml/Main.fxml");
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
