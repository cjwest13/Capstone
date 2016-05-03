package API.Base;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

import static java.lang.Math.random;

/**
 * Base Case for JavaFX applications to base themselves off of.
 * Folders/Packages should be setup and named in this way if needed to run on the kiosk: <br>
 * fxml: package containing fxml for screens; your first screen <b>MUST </b> be called main.fxml
 * (Capitalization doesn't matter). <br>
 * controller: package containing controller classes for fxml screens. Controller for first class
 * <b>MUST </b>be called Main.java or your plugin will not run (Capitalization DOES MATTER in this case). <br>
 * App.txt: (Capitalization doesn't matter) Not in any package; should contain: <br>
 * First line: Name of Application (Do not put "Name: blah blah blah", only "blah blah blah")<br>
 * Rest of the lines in the file: Are available for the description (Again do not put "description: blahhh",
 * only "blahhh") <br>
 * preview: folder containing the preview picture of the application.(Capitalization doesn't matter). <br>
 * icon: folder containing the icon photo of the application.(Capitalization doesn't matter). <br>
 * Use stage.close method to exit the application or use the {@link #close()} method in this Class. <br>
 * Other than that, go crazy!!!!!!!!!!!!!!!  (: #TeamJavaFX
 * @author  Clifton West
 * @version February 1, 2016
 */
public class App extends Application {

    /** Arraylist of current observers*/
    private static ArrayList<controller.Observers.Observer> observers = new ArrayList<>();

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

    private static controller.Classes.Sound sound;
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
     * Setting the Sound
     * @param s the sound object begin set.
     */
    public static void setSound(controller.Classes.Sound s) {
        sound = s;
    }

    /**
     * Returns sound object.
     * @return sound
     */
    public static controller.Classes.Sound getSound() {
        return sound;
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
     * @param fxml1     Path of the fxml
     * @param title1    String containing the title.
     */
    public static void setFxmlAndTitle(String fxml1, String title1) {
        title = title1;
        fxml = fxml1;
    }

    /**
     * Goes to the screen according to the fxml when called.
     * Close pattern is automatically implemented.
     * @param stage Stage object.
     * @param title String containing the title.
     * @param fxml1  Path of the fxml.
     */
    private void goToNextScreen(Stage stage, String title, String fxml1) {
        Parent loadScreen;
        Timeline timeline = new Timeline();
        EventHandler handler = event1 -> { timeline.stop(); timeline.play(); };
        KeyFrame key = new KeyFrame(Duration.minutes(5), event -> {
            stage.removeEventHandler(MouseEvent.ANY, handler);
            goToScreenSaver(fxml);
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
            stage.addEventHandler(MouseEvent.ANY, handler);
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
     * @param time Date object containing the current time.
     */
    private void setTime(Date time) {
        this.time = time;
        notifyObservers();
    }

    /**
     * Static Method for Application's to add new observers.
     * @param observer New Observer.
     */
    public static void addObserver(controller.Observers.Observer observer) {
        observers.add(observer);
    }

    /**
     * Static Method for Applications to remove current Observers.
     * @param observer Current Observer.
     */
    public static void removeObserver(controller.Observers.Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies current observers about the time change.
     */
    private void notifyObservers() {
        for (controller.Observers.Observer ob: observers) {
            ob.update(time);
        }
    }

    /**
     * A private method that goes to the screensaver screen via the fxml file that is passed.
     * Goes back to the screen that is currently loaded in the curfxml variable.
     * @param curfxml   path to fxml file that left of on.
     */
    private void goToScreenSaver(String curfxml) {
        EventHandler handler1 = new EventHandler() {
            @Override
            public void handle(Event event) {
                stage.removeEventHandler(MouseEvent.MOUSE_PRESSED, this);
                goToNextScreen(stage, title, curfxml);
            }
        };
        stage.addEventHandler(MouseEvent.MOUSE_PRESSED, handler1);
        buildScreenSaver();
    }

    public void buildScreenSaver() {
        if (sound != null) {
            sound.endAllSounds();
        }
        FadeTransition fd = new FadeTransition(Duration.millis(2000));
        fd.setOnFinished(event -> {});
        Group root = new Group();
        Scene scene = new Scene(root, 1000, 1000, Color.BLACK);

        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        Group circles = new Group();
        for (int i = 0; i < 30; i++) {
            Circle circle = new Circle(150, Color.web("white", 0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);
            circles.getChildren().add(circle);
        }
        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new Stop[]{
                        new Stop(0, Color.web("#f8bd55")),
                        new Stop(0.14, Color.web("#c0fe56")),
                        new Stop(0.28, Color.web("#5dfbc1")),
                        new Stop(0.43, Color.web("#64c2f8")),
                        new Stop(0.57, Color.web("#be4af7")),
                        new Stop(0.71, Color.web("#ed5fc2")),
                        new Stop(0.85, Color.web("#ef504c")),
                        new Stop(1, Color.web("#f2660f")),}));
        colors.widthProperty().bind(scene.widthProperty());
        colors.heightProperty().bind(scene.heightProperty());
        Group blendModeGroup =
                new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight(),
                        Color.BLACK), circles), colors);
        colors.setBlendMode(BlendMode.OVERLAY);
        root.getChildren().add(blendModeGroup);
        circles.setEffect(new BoxBlur(10, 10, 3));
        Timeline timeline = new Timeline();
        for (Node circle : circles.getChildren()) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0
                            new KeyValue(circle.translateXProperty(), random() * 1000),
                            new KeyValue(circle.translateYProperty(), random() * 1000)),
                    new KeyFrame(new Duration(60000), // set end position at 60s
                            new KeyValue(circle.translateXProperty(), random() * 1000),
                            new KeyValue(circle.translateYProperty(), random() * 1000)));
        }
        //loops through the animation indefinitely
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
        stage.show();
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
    }

    /**
     * Close method for application to use.
     */
    public static void close() {
        Platform.exit();
        System.exit(0);
    }
}