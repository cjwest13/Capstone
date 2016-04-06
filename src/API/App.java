package API;

import controller.ComponentControl;
import controller.GestureControl;
import controller.*;
import controller.Observer;
import controller.Sound;
import controller.SystemData;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

/**
 * Base Case for applications to base themselves off of.
 * API for the accessing the lifecycle methods.
 * @author  Clifton West
 * @version February 1, 2016
 */
public class App extends Application implements ComponentControl, SystemData, Sound {

    /** Arraylist of current observers*/
    private static ArrayList<Observer> observers = new ArrayList<>();

    /** TImer Object */
    Timer timer = new Timer();

    /** Title of Stage */
    private static String title;

    /** fxml being loaded */
    private static String fxml;

    /** Window where the screen is being placed */
    private static Stage stage;


    /** Date object that will hold the current date/time */
    private Date time;

    private GestureControl gestures = new Gestures();

    /**
     * Initializes the Main screen.
     * @param primaryStage  The current Stage.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        runTime();
        stage = primaryStage;
        goToNextScreen(primaryStage, title, fxml);

    }

    /**
     * Method that updates the time each second.
     */
    public void runTime() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Calendar calendar = Calendar.getInstance();
                    Date date = calendar.getTime();
                    setTime(date);
                });
            }
        }, 1000, 1000);
    }

    /**
     * Returns the application's current stage.
     * @return Stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Sets the fxml of the new screen.
     * @param fxml1 Path of the fxml.
     */
    public static void setFxml(String fxml1) {
        fxml = fxml1;
    }

    /**
     * Sets the title of the stage.
     * @param title1 String containing the title.
     */
    public void setTitle(String title1) {
        title = title1;
    }

    /**
     * Method for the Application to set the Fxml and the Title.
     * @param title1    String containing the title.
     * @param fxml1     Path of the fxml.
     */
    public static void setFxmlAndTitle(String title1, String fxml1) {
        title = title1;
        fxml = fxml1;
    }

    /**
     * Goes to the screen according to the fxml when called.
     * @param stage Stage object.
     * @param title String containing the title.
     * @param fxml1  Path of the fxml.
     */
    private void goToNextScreen(Stage stage, String title, String fxml1) {
        Parent loadScreen;
        Timeline timeline = new Timeline();
        EventHandler handler = event1 -> { timeline.stop(); timeline.play(); };
        KeyFrame key = new KeyFrame(Duration.seconds(20), event -> {
            stage.removeEventHandler(MouseEvent.ANY, handler);
            goToScreenSaver("Screensaver.fxml", fxml);
        });
        timeline.getKeyFrames().add(key);
        try {
            loadScreen = FXMLLoader.load(getClass().getResource(fxml1));
            FadeTransition ft = new FadeTransition(Duration.millis(3000), loadScreen);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Scene scene = new Scene(loadScreen);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                gestures.mouseEntered(event.getX(), event.getY());
            });
            stage.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
                int value = gestures.diagonalSwipe(event.getX(), event.getY());
                if (value == 3) {
                    System.out.println("AYY CLOSEEE");
                    stage.close();
                }
            });
            stage.addEventHandler(MouseEvent.ANY, handler);
            //addEvents(stage);
            timeline.play();
            stage.show();
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
        } catch (IOException ioe) {
            System.err.println("File not found");
        }
    }

    /**
     * Setter for the time.
     * @param time  Date object containing the current time.
     */
    private void setTime(Date time) {
        this.time = time;
        notifyObservers();
    }

    /**
     * Static Method for Application's to add new observers.
     * @param observer  New Observer.
     */
    public static void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Static Method for Applications to remove current Observers.
     * @param observer Current Observer.
     */
    public static void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies current observers about the time change.
     */
    private void notifyObservers() {
        for (Observer ob: observers) {
            ob.update(time);
        }
    }

    /**
     * Saves the state of the application.
     */
    public static void saveState() {

    }

    /**
     * To load current of an application.
     */
    public static void loadState() {

    }

    /**
     * A default method that goes to the screensaver screen via the fxml file that is passed.
     * @param fxml      path to an fxml file.
     * @param curfxml   path to fxml file that left of on.
     */
    private void goToScreenSaver(String fxml, String curfxml) {
        Parent loadScreen;
        try {
            loadScreen = FXMLLoader.load(getClass().getResource(fxml));
            FadeTransition ft = new FadeTransition(Duration.millis(3000), loadScreen);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Scene scene = new Scene(loadScreen);
            EventHandler handler = new EventHandler() {
                @Override
                public void handle(Event event) {
                    stage.removeEventHandler(MouseEvent.MOUSE_PRESSED, this);
                    goToNextScreen(stage, title, curfxml);
                }
            };
            stage.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
            stage.setScene(scene);
            stage.show();
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);

        } catch (IOException ioe) {
            System.err.println("File not found");
        }
    }

}