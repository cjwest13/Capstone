package project;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the AddPlugs.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 4, 2015
 */
public class AddPlugsController implements Initializable{
    /** TestArea representing the file text area in the fxml */
    @FXML
    private TextArea textArea;
    /** Dialog popup box */
    Dialog<String> dialog;
    /** Close Button for the Dialog box */
    private ButtonType close;
    /** The file selected */
    File file;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog = new Dialog<>();
        close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);

    }

    /**
     *  Finds the jar javafx file of a new plugin.
     */
    @FXML
    public void findPlugin() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose File");
        file = chooser.showOpenDialog(MainScreen.getStage());
    }

    /**
     * Adds the plugin.
     */
    @FXML
    public void addPlugin() {
        //Do stuff to add the plugin.
        goToSettings();
    }

    /**
     * Private method to create the dialog popup box.
     * @param title     Title of the dialog box.
     * @param message   Message inside of the dialog box.
     */
    private void dialog(String title, String message) {
        dialog.getDialogPane().getButtonTypes().add(close);
        dialog.setTitle(title);
        dialog.setHeight(200);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    /**
     * Function assigned to a fxml button that goes to the Settings.fxml screen.
     */
    @FXML
    public void goToSettings() {
        Parent loadScreen;
        try {
            loadScreen = FXMLLoader.load(getClass().getResource("/fxml/Settings.fxml"));
            FadeTransition ft = new FadeTransition(Duration.millis(3000), loadScreen);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Scene scene = new Scene(loadScreen);
            Stage stage = MainScreen.getStage();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException ioe) {
            System.err.println("File not found");
        }

    }
}
