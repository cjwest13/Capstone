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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    private File file;

    private static List<List<File>>  pluginsMain;

    private static int numOfPlugins;
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pluginsMain = new ArrayList<>();
        file = new File("/home/cjwest/Documents/Mine/KioskCapstone/resources");
        isDirectory();
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

    private void isDirectory() {
        File[] list = file.listFiles();
        List<List<File>> packages = new ArrayList<>();
        List<List<File>> files = new ArrayList<>();
        numOfPlugins = list.length;
        if (list.length == 0) {
            System.out.println("Directory is empty");
        } else {
            System.out.println("Stuff in Directory");
            for (int i = 0; i < list.length; i ++) {
                File[] pack = list[i].listFiles();
                packages.add(new ArrayList<>());
                //System.out.println("Stuff in Packages");
                for (int j = 0; j < pack.length; j++) {
                    packages.get(i).add(pack[j]);
                }
            }

            for (int i = 0; i < packages.size(); i++) {
                Iterator iterator = packages.get(i).iterator();
                files.add(new ArrayList<>());
                while (iterator.hasNext()) {
                    File file = (File) iterator.next();
                    System.out.println(file.getName());
                    if ("fxml".equals(file.getName().toLowerCase())) {
                        files.get(i).add(file);
                    }
                }
            }

            for (int i = 0; i < files.size(); i ++) {
                File[] file = (files.get(i).get(0)).listFiles();
                pluginsMain.add(new ArrayList<>());
                for (int k = 0; k < file.length; k++) {
                    if ("main.fxml".equals(file[k].getName().toLowerCase())) {
                        pluginsMain.get(i).add(file[k]);
                    }
                }
            }
        }
    }

    public static List<List<File>> getPluginsMain() {
       return pluginsMain;
    }

    public static int getNumOfPlugs() {
        return numOfPlugins;
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
