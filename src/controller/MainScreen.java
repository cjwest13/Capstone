package controller;


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
    /** */
    static Gestures gestures = new Gestures();
    /** */
    static int horzValue;

    /**
     * Initializes the Main screen.
     //* @param primaryStage  The current Stage.
     * @throws Exception

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;
        //goToNextScreen("/fxml/Proof.fxml");
        //goToNextScreen("/fxml/Proof2.fxml");
        goToNextScreen("/fxml/Main.fxml");
    }
     */
    public static void fxml(String fxml) {
        setFxml(fxml);
    }

    /**
     * Returns the current stage.
     * @return stage    The current stage.
     */
    public static Stage getCurrentStage() {
        stage = getStage();
        //PauseTransition holdTimer = gestures.pressHold();
        //holdTimer.setOnFinished( event1 -> {System.out.println("HOLD DAT TAPPP");});
        /**
        stage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int value = gestures.doubleClick(event.getX(), event.getY());
            if (value == 2) {
                System.out.println("DOUBBLEEE");
                event.consume();
           }
        });

        stage.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            gestures.mouseEntered(event.getX(), event.getY());
            holdTimer.playFromStart();
            //hold.value = gestures.pressHold(event.getX(), event.getY(), true);
            //if (hold.value == 1) {
              //  System.out.println("HOLD DATT");
            //}
        });

        stage.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            holdTimer.stop();
            int value = gestures.diagonalSwipe(event.getX(), event.getY());
            int vertValue = gestures.verticalSwipe(event.getX(), event.getY());
            horzValue = gestures.horizontalSwipe(event.getX(), event.getY());
            if (value == 1) {
                System.out.println("L2R Diagonal Swipe");
            } else if (value == 2) {
                System.out.println("R2L Diagonal Swipe");
            } else if (value == 3) {
                System.out.println("AYY CLOSEEE");
            }
            if (vertValue == 1) {
                System.out.println("Up to Down Swipe");
            } else if (vertValue == 2) {
                System.out.println("Down to Up Swipe");
            }
            //if (horzValue == 1) {
            //    System.out.println("Left to Right Swipe");
            //} else if (horzValue == 2) {
           //     System.out.println("Right to Left Swipe");
           // }
        });

        stage.addEventHandler(MouseEvent.DRAG_DETECTED, event -> {
            holdTimer.stop();
        });

        //addEvents(stage);
        //System.out.println("NOO");
        //stage.addEventHandler(new EventType<T extends Event>(), event -> {});
        //stage.ad
         */
        return stage;
    }

    /**
     * Adding New Observer
     * @param observer
     */
    public static void addNewObserver(Observer observer) {
        addObserver(observer);
        //System.out.println("NEW");
    }

    /**
     * Remove An Observer
     * @param observer
     */
    public static void removeNewObserver(Observer observer) {
        removeObserver(observer);
    }

    /**
     * Entry point into the program.
     * @param args
     */
    public static void main(String[] args) {
        //setFxmlAndTitle("Capstone", "/fxml/Main.fxml");
        launch(args);

    }
}
