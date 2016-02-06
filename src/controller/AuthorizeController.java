package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import utilities.NextScreen;
import utilities.PasswordEncryption;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller class for Authorization.fxml file.
 * @author  Clifton West
 * @version October 4, 2015
 */
public class AuthorizeController implements Initializable, NextScreen {

    /** TestField representing the username text field in the fxml */
    @FXML
    private TextField username;

    /** PasswordField representing the password password field in the fxml */
    @FXML
    private PasswordField password;

    /** String representing the admin username */
    private String adminname;

    /** String representing the admin password */
    private static Boolean newAdminPw;

    /** String representing the admin password */
    public static String encryptedAdminPw;

    /** Dialog popup box */
    private Dialog<String> dialog = new Dialog<>();

    /** Close Button for the Dialog box */
    private ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);

    /** Label representing the label containing the time in the fxml */
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
        time();
        adminname = "admin";
        if (newAdminPw == null) {
            encryptedAdminPw = PasswordEncryption.getEncryptedPw("touch");
            newAdminPw = false;
        }
    }

    /**
     * Animation to show the time.
     */
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
     * Changes the password.
     * @param text  The new password.
     */
    public static void changePassword(String text) {
        encryptedAdminPw = PasswordEncryption.getEncryptedPw(text);
        newAdminPw = true;
    }

    /**
     * Function assigned to a fxml button that goes to the Main.fxml screen.
     */
    @FXML
    public void goToMain() {
        NextScreen.super.goToNextScreen("/fxml/Main.fxml");
    }

    /**
     * Checks to see if the username and password enter, matches the one
     * already saved.
     */
    @FXML
    public void getInfo() {
        String newPw = PasswordEncryption.getEncryptedPw(password.getText());
        if (!username.getText().equals(adminname)) {
            dialog("Incorrect Information", "The Username is Incorrect.");
        } else if (!newPw.equals(encryptedAdminPw)) {
            dialog("Incorrect Information", "The Password is Incorrect.");
        } else {
            goToSettings();
        }
    }

    /**
     * Function assigned to a fxml button that goes to the Settings.fxml screen.
     */
    private void goToSettings() {
        NextScreen.super.goToNextScreen("/fxml/Settings.fxml");
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
}