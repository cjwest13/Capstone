package project;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.NextScreen;

import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the PluginScreen.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 3, 2015
 */
public class PluginScreenController implements Initializable, NextScreen {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private HBox hbox;

    private static List<List<File>> pluginsMain;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pluginsMain = MainController.getPluginsMain();
        anchorPane.setOnSwipeDown(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                Platform.exit();
            }
        });
        hbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String hi = pluginsMain.get(0).get(0).getAbsolutePath();
                String[] no = hi.split("/resources");
               // String ma = "../1/fxml/Main.fxml";
                //String ma = "/home/cjwest/Documents/resources/1/fxml";
                goToNextScreen(no[1]);
                /**
                try {
                   //System.out.println(pluginMain.getPath());
                    //System.out.println(pluginMain.getCanonicalPath());
                   goToNextScreen(pluginMain.getCanonicalPath());
                    //goToNextScreen(pluginMain.getPath());
                } catch (IOException ioe) {
                //    System.err.println("File not found");
                }
                 */
            }
        });
    }

    /**
     * Function assigned to a fxml button that goes to the Main.fxml screen.
     */
    @FXML
    public void goToMain() {
        goToNextScreen("/fxml/Main.fxml");
    }

    /**
     * Function assigned to a fxml grid that loads the plugin's main screen.
     * @param fxml path to an fxml file.
     */
    public void goToPlugin(String fxml) {
       //NextScreen.;
    }
}