package utilities;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by cjwest on 3/27/16.
 */
public class addEvents {
    private double firstX;
    private double firstY;
    private double secX;
    private double secY;

    public void addStageEvents(Stage stage) {
        stage.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            firstX = event.getX();
        });
    }

}
