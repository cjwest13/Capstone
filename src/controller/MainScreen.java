package controller;


import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import utilities.NextScreen;
/**
 * Makes the main and first screen showed.
 * @author  Clifton West
 * @version October 3, 2015
 */
public class MainScreen extends App implements NextScreen {
    /** A static variable containing the current stage */
    private static Stage stage;
    
    private static Timeline timeline;
    /**
     */
    public static void fxml(String fxml) {
        //setFxml(fxml);
    }

    /**
     * Returns the current stage.
     * @return stage The current stage.
     */
    public static Stage getCurrentStage() {
        return stage;
    }
     
     public static Timeline time() {
    	 return timeline;
    	 //removeEvent();
     }
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	runTime();
    	stage = primaryStage;
    	setStage(stage);
    	setFxmlAndTitle("/fxml/Main.fxml", "Capstone");
    	//screenSaver();    	
    	goToNextScreen("/fxml/Main.fxml");
    	
    }
    
    /**
     * Entry point into the program.
     * @param args
     */
    public static void main(String[] args) {
        //stage = new Stage();
        //goToScreen(stage, "Capstone", "fxml/Main.fxml");
    	//setFxmlAndTitle("/fxml/Main.fxml", "Capstone");
        launch(args);
       //stage = getStage();
    }
}
