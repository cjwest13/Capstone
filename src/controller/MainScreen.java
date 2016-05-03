package controller;


import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.NextScreen;
/**
 * Makes the main and first screen showed.
 * @author  Clifton West
 * @version October 3, 2015
 */
public class MainScreen extends App implements NextScreen {
    
    /**
     * Entry point into the program.
     * @param args
     */
    public static void main(String[] args) {
    	setFxmlAndTitle("/fxml/Main.fxml", "Capstone");
        launch(args);
    }
}
