package project;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.NextScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for Main.fxml.
 * @author  Clifton West, John Burrell
 * @version October 3, 2015
 */
public class MainController implements Initializable, NextScreen {
    /** Label representing a label in the fxml */
    @FXML
    private Label label1txt;
    /** AnchorPane representing the anchorpane in the fxml */
    @FXML
    private AnchorPane anchorPane;
    /** BUtton representing the settings button in the fxml */
    @FXML
    private Button settingbtn;
    /** TestAera representing the greeting text area in the fxml */
    @FXML
    private TextArea greetTxt;
    /** String containing the greeting. */
    public static String greeting;
    @FXML
    private GridPane gridPane;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeGreeting(greeting);
        label1txt.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            /**
             * Mouse Clicked event for label1txt.
             * @param event MouseClicked event
             */
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                goToNextScreen("/fxml/PluginScreen.fxml");
            }
        });
        /** Sets the settings button to be visible */
        anchorPane.setOnKeyPressed(ke-> {
            if (ke.getCode().equals(KeyCode.K)) {
                settingbtn.setVisible(true);
            }
        });
        //dynamically adding
        int i = 3;
        int j = 0;
        while (j < 20) {
        gridPane.add(new Button(), 1, i);
            i++;
            j++;
        }
    }

    /**
     * Changes the main Greeting.
     * @param text The text for the new greeting.
     */
    private void changeGreeting(String text) {
        if (greeting == null) {
            greetTxt.setText("Hello!");
        } else {
            greeting = text;
            System.out.println(greeting);
            System.out.println(text);
            greetTxt.setText(greeting);
        }
    }

    /**
     * Function assigned to a fxml button that goes to the Authorization.fxml screen.
     */
    @FXML
    public void goToSettings() {
        NextScreen.super.goToNextScreen("/fxml/Authorization.fxml");
    }
}
