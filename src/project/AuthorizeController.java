package project;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for Authorization.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 4, 2015
 */
public class AuthorizeController implements Initializable {
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

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminname = "admin";
        if (newAdminPw == null) {
            encryptedAdminPw = PasswordEncryption.getEncryptedPw("touch");
           newAdminPw = false;
        }
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
        goToNextScreen("/fxml/Main.fxml");
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
        goToNextScreen("/fxml/Settings.fxml");
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
     * Goes to the screen according to the fxml file that is passed.
     * @param fxml path to an fxml file.
     */
    private void goToNextScreen(String fxml) {
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
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException ioe) {
            System.err.println("File not found");
        }

    }
}
