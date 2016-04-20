package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import utilities.NextScreen;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller class for Password.fxml file.
 * @author  Clifton West
 * @version October 4, 2015
 */
public class PasswordController extends AuthorizeController implements Observer, Initializable, NextScreen {

    /** PasswordField representing the password password field in the fxml */
    @FXML
    private PasswordField password;

    /** Dialog popup box */
    Dialog<String> dialog = new Dialog<>();

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
        MainScreen.addObserver(this);
    }

    /**
     * Changes the Password for the admin.
     */
    @FXML
    public void changePassword() {
        if (password.getText() == null) {
            dialog("Incorrect Information", "Enter A Password.");
        } else {
            changePassword(password.getText());
            dialog("Confirmation", "Password was changed!");
        }
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
    public void goBack() {
        MainScreen.removeObserver(this);
        NextScreen.super.goToNextScreen("/fxml/Settings.fxml");
    }

    /**
     * Update the time to the time label.
     * @param date Date object that is passed.
     */
    @Override
    public void update(Date date) {
        timeLbl.setText(date.toString());
    }
}