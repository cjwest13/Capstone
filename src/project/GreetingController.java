package project;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.NextScreen;

import java.io.*;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for Greeting.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 5, 2015
 */
public class GreetingController implements Initializable, NextScreen {
    /** TestField representing the greeting text field in the fxml */
    @FXML
    private TextField newgreet;
    /** Dialog popup box */
    private Dialog<String> dialog;
    /** Close Button for the Dialog box */
    private ButtonType close;
    /** The file selected */
    File openfile;
    /** If File is being used */
    private Boolean value;
    @FXML
    private Label timeLbl;

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
        value = false;
        time();
    }

    private void time() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), event -> {
            Calendar calendar = Calendar.getInstance();
            Date time = calendar.getTime();
            timeLbl.setText(time.toString());
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Set MainController greeting variable to the new greeting. Dialog box
     * pops up that confirms the greeting was changed.
     */
    @FXML
    public void greeting() {
        if (value) {
            MainController.greeting = readFile(openfile);
        } else {
            MainController.greeting = newgreet.getText();
        }
        dialog("Confirmation", "Greeting was changed!");
    }

    /**
     * Reads the txt file.
     * @param   file            File to be read.
     * @return  stringBuffer    The string returned.
     */
    private String readFile(File file) {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
        } catch (Exception ex) {
                dialog("Error", "Greeting can not be changed.");
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                dialog("Error", "Greeting can not be changed.");
            }
        }
        return stringBuffer.toString();
    }

    /**
     * Opens The File Directory to select the text file.
     */
    @FXML
    public void findFile() {
        value = true;
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose File");
        openfile = chooser.showOpenDialog(MainScreen.getStage());
        newgreet.setText(openfile.toString());
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
     * Goes to the Settings.fxml screen.
     */
    @FXML
    public void goBack() {
        NextScreen.super.goToNextScreen("/fxml/Settings.fxml");
    }
}
