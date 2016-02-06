package controller;


import javafx.application.Application;
import javafx.stage.Stage;
import utilities.NextScreen;

/**
 * Makes the main and first screen showed.
 * @author  Clifton West
 * @version October 3, 2015
 */
public class MainScreen extends Application implements NextScreen{

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
        goToNextScreen("/fxml/Main.fxml");
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
