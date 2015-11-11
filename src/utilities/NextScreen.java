package utilities;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.MainScreen;

import java.io.IOException;

/**
 * Created by cjwest on 11/2/15.
 */
public interface NextScreen {

    /**
     * A default method that goes to the screen according to the fxml file that is passed.
     * @param fxml path to an fxml file.
     */
    default void goToNextScreen(String fxml) {
        Parent loadScreen;
        try {
            loadScreen = FXMLLoader.load(getClass().getResource(fxml));
            FadeTransition ft = new FadeTransition(Duration.millis(3000), loadScreen);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Scene scene = new Scene(loadScreen);
            Stage stage = MainScreen.getStage();
            stage.setScene(scene);
            stage.show();
            //stage.setFullScreenExitHint("");
            //stage.setFullScreen(true);

        } catch (IOException ioe) {
            System.err.println("File not found");
        }
    }
}
