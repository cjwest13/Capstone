package utilities;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import controller.Main;
import java.io.IOException;

/**
 * Next Screen Interface.
 * @author  Clifton West
 * @version November 3, 2015
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
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
            //Main.setFxml(fxml);
        } catch (IOException ioe) {
            System.err.println("File not found");
        }
    }
    default void goToNewScreen(Stage stage, String title, String fxml1) {
    	Parent loadScreen;
    	try {
            loadScreen = FXMLLoader.load(getClass().getResource(fxml1));
            FadeTransition ft = new FadeTransition(Duration.millis(3000), loadScreen);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Scene scene = new Scene(loadScreen);
            stage.setScene(scene);
            stage.setTitle(title);
//            stage.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
//                gestures.mouseEntered(event.getX(), event.getY());
//            });
//            stage.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
//                int value = gestures.diagonalSwipe(event.getX(), event.getY());
//                if (value == 3) {
//                    //System.out.println("AYY CLOSEEE");
//                    stage.close();
//                }
//            });
            //stage.addEventHandler(MouseEvent.ANY, handler);
            //addEvents(stage);
            //timeline.play();
            stage.show();
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
        } catch (IOException ioe) {
            System.err.println("File not found");
        }
    }
}
