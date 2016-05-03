package utilities;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import controller.MainScreen;
import java.io.IOException;

/**
 * Class that goes to the screen specified by the fxml file.
 * @author Clifton West
 * @version November 2, 2015
 */
public interface NextScreen {

    /**
     * A default method that goes to the screen according to the fxml file that is passed.
     * @param fxml path to an fxml file.
     */
    default void goToNextScreen(String fxml) {
        Parent loadScreen = null;
        Stage stage = MainScreen.getStage();
        try {
            loadScreen = FXMLLoader.load(getClass().getResource(fxml));
            FadeTransition fadeIn = new FadeTransition(Duration.millis(3000), loadScreen);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
            Scene scene = new Scene(loadScreen);
            stage.setScene(scene);
            stage.show();
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
            MainScreen.setFxml(fxml);
        } catch (IOException ioe) {
            System.err.println("File not found");
        }
    }
}
