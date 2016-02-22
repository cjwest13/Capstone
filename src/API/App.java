package API;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Base Case for applications to base themselves off of.
 * API for the accessing the lifecycle methods.
 * @author  Clifton West
 * @version February 1, 2016
 */
public abstract class App implements WebData, AppPersistence, ComponentControl, GestureControl, SystemData, Sound {

    /** Boolean to see if the app launched successfully */
    private Boolean launch = false;

    /** Boolean to see if the app closed */
    private Boolean close = false;

    /** Boolean to see if the app was pause */
    private Boolean pause = false;

    /** Boolean to see if the app was resumed */
    private Boolean resume = false;

    /**  */
    private Parent root;

    /** */
    private static String title;

    /** */
    private static String fxml;

    /** */
    private Stage stage;

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HELLOOOOOOO");
        setRoot("sample.fxml");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreen(true);
        stage = primaryStage;
    }

    /**
     *
     * @param fxml
     * @throws Exception
     */
    private void setRoot(String fxml) throws Exception {
        root = FXMLLoader.load(getClass().getResource(fxml));
    }


    public Stage getStage() {
        return stage;
    }


    static void help(String title1, String fxml1) {
        title = title1;
        fxml = fxml1;
    }

    /**
     * Setter for the launch variable.
     * @param lanch Boolean if the app was launch successfully.
     */
    public void launchApp(Boolean lanch) {
        this.launch = lanch;
    }

    /**
     * Setter for the close variable.
     * @param close Boolean if the app was closed.
     */
    public void closeApp(Boolean close) {
        this.close = close;
    }

    /**
     * Setter for the pause variable.
     * @param pause Boolean if the app was paused.
     */
    public void pauseApp(Boolean pause) {
        this.pause = pause;
    }

    /**
     * Setter for the resumed variable.
     * @param resume Boolean if the app was resumed.
     */
    public void resumeApp(Boolean resume) {
        this.resume = resume;
    }

    /**
     * Getter for the launch variable.
     * @return Boolean if the app was launched.
     */
    public Boolean appDidLaunch() {
        return launch;
    }

    /**
     * Getter for the closed variable.
     * @return Boolean if the app was closed.
     */
    public Boolean appDidClose() {
        return close;
    }

    /**
     * Getter for the paused variable.
     * @return Boolean if the app was paused.
     */
    public Boolean appDidPause() {
        return pause;
    }

    /**
     * Getter for the resumed variable.
     * @return Boolean if the app was resumed after pausing.
     */
    public Boolean didAppResume() {
        return resume;
    }
}