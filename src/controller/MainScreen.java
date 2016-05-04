package controller;


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
