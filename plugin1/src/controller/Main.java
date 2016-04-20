package controller;

import javafx.stage.Stage;
import utilities.NextScreen;

/**
 * Main class of the Application.
 * @author  Clifton West
 * @version November 3, 2015
 */
public class Main extends App implements NextScreen {

    /**
     * Entry point of the application.
     * @param args
     */
    public static void main(String[] args) {
        setFxmlAndTitle("/fxml/main.fxml", "Scheduler Viewer");
        launch(args);
    }
}
