package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import utilities.NextScreen;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import API.SystemData;

/**
 * Controller class for Main.fxml.
 * @author  Clifton West
 * @version October 3, 2015
 */
public class MainController implements Initializable, NextScreen, SystemData {
    /** Label representing a label in the fxml */

    /** AnchorPane representing the anchorpane in the fxml */
    @FXML
    private AnchorPane anchorPane;
    /** BUtton representing the settings button in the fxml */
    @FXML
    private Button settingbtn;
    /** TestAera representing the greeting text area in the fxml */
    @FXML
    private TextArea greetTxt;

    /** GridPane representing the center grid in the fxml */
    @FXML
    private GridPane gridPane;

    /** Label representing the label containing the time in the fxml */
    @FXML
    private Label timeLbl;

    /** String containing the greeting. */
    public static String greeting;

    /** 2D Arraylist containing the jar file, and preview folder for each plugin */
    private List<List<File>> plugins;

    /** Arraylist of the icon files */
    private ArrayList<File> icons;

    /** A file array containing the chosen plugin's jar file and preview folder */
    private static File[] chosenPlugin;

    /** the number of plugins */
    private static int numOfPlugins;

    /** An arraylist of the labels in the grid */
    private ArrayList<Label> labels;

    /** An File array containing the folders for each plugin */
    File[] list;
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chosenPlugin = new File[2];
        plugins = new ArrayList();
        labels = new ArrayList();
        icons = new ArrayList();
        addPlugins();
        setEvents();
        changeGreeting(greeting);
        time();
        /** Sets the settings button to be visible on key pressed */
        anchorPane.setOnKeyPressed(ke-> {
            if (ke.getCode().equals(KeyCode.K)) {
                settingbtn.setVisible(true);
            }
        });
    }

    /**
     * Animation to show the time.
     */
    private void time() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), event -> {
            timeLbl.setText(getDateAndTime());
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Sets the events for each label in the grid.
     */
    private void setEvents() {
        for (Label label: labels) {
            label.setOnMouseClicked(event ->  {
                int index = gridPane.getRowIndex(label);
                chosenPlugin[0] = plugins.get(index).get(0);
                chosenPlugin[1] = plugins.get(index).get(1);
                //goToNextScreen("/fxml/PluginInfo.fxml");
                goToNextScreen("/fxml/Proof2.fxml");
            });
        }
    }

    /**
     * Adds the plugins to the grid.
     */
    private void addPlugins() {
        if (isPlugins()) {
            loadPlugins();
            Image defaultimage = new Image("/utilities/cone.png");
            //dynamically adding
            for (int i = 0; i < plugins.size(); i++) {
                File[] files;
                ImageView view;
                if (icons.size() <= i) {
                    view = new ImageView(defaultimage);
                } else {
                    files = icons.get(i).listFiles();
                    if (files.length == 0) {
                        view = new ImageView(defaultimage);
                    } else {
                        view = new ImageView(defaultimage);
                        Image image;
                        try {
                            BufferedImage bufimage = ImageIO.read(files[0]);
                            image = SwingFXUtils.toFXImage(bufimage, null);
                        } catch (IOException ioe) {
                            image = new Image("/utilities/cone.png");
                        }
                        view = new ImageView(image);
                    }
                }
                labels.add(new Label("Name of App "+i));
                gridPane.add(labels.get(i), 1, i);
                view.setFitHeight(150);
                view.setPreserveRatio(true);
                gridPane.add(view, 0, i);
            }
        }
    }

    /**
     * Checks to see if they are plugins in the resource folder.
     * @return  Boolean returns true if they are plugins, false if its not.
     */
    private Boolean isPlugins() {
        //File file = new File("/home/touchmeister/resources");
        File file = new File("/home/cjwest/resources");
        list = file.listFiles();
        numOfPlugins = list.length;
        if (list.length == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Adding the appropriate files for each plugin to the plugin 2D arraylist.
     */
    private void loadPlugins() {
        List<List<File>> packages = new ArrayList<>();
        //going through each plugin folder
        for (int i = 0; i < list.length; i ++) {
            File[] pack = list[i].listFiles();
            packages.add(new ArrayList());
            for (int j = 0; j < pack.length; j++) {
                packages.get(i).add(pack[j]);
            }
        }
        //looking for preview folder, icon folder and jar file for each plugin.
        for (int i = 0; i < packages.size(); i++) {
            Iterator iterator = packages.get(i).iterator();
            plugins.add(new ArrayList());
            while (iterator.hasNext()) {
                File file = (File) iterator.next();
                if ("preview".equals(file.getName().toLowerCase())) {
                    plugins.get(i).add(file);
                } else if ("icon".equals(file.getName().toLowerCase())) {
                    icons.add(file);
                } else {
                    String[] string = file.getName().split("\\.");
                    if (string.length > 1 && "jar".equals(string[1])) {
                        plugins.get(i).add(file);
                    }
                }
            }
        }
    }

    /**
     * A static method to get the files of the chosen plugin.
     * @return  File[] the chosen plugin.
     */
    public static File[] getChosenPlugin() {
        return chosenPlugin;
    }

    /**
     * A static method to get the number of plugins.
     * @return int the number of plugins.
     */
    public static int getNumOfPlugins() {
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