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
import java.io.*;
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


    private  List<List<String>> appData;

    /** A file array containing the chosen plugin's jar file and preview folder */
    private static File[] chosenPlugin;

    /** the number of plugins */
    private static int numOfPlugins;

    /** An arraylist of the labels in the grid */
    private ArrayList<Label> labels;


    private File appTxt;

    private List<List<File>> preview;

    private static File chosenPreview;

    private static String description;

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
        appData = new ArrayList();
        plugins = new ArrayList();
        labels = new ArrayList();
        icons = new ArrayList();
        preview = new ArrayList();
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
                chosenPreview = preview.get(index).get(0);
                description = appData.get(index).get(1);
                goToNextScreen("/fxml/PluginInfo.fxml");
            });
        }
    }

    /**
     * Adds the icon and name of application to the grid.
     */
    private void addPlugins() {
        if (isPlugins()) {
            loadPlugins();
            //Default Icon
            Image defaultimage = new Image("/utilities/cone.png");
            //Going through the plugin 2d Arraylist
            for (int i = 0; i < plugins.size(); i++) {
                File[] files;
                ImageView view;
                //check to see if there are enough icons in the icons array
                /**
                 * FIX CASE (TURN ICONS IN 2D ARRAYLIST SOON)
                 */
                if (icons.size() < i || (icons.size() == i && i == 0)) {
                    view = new ImageView(defaultimage);
                } else {
                    files = icons.get(i).listFiles();
                    if (files.length == 0) {
                        view = new ImageView(defaultimage);
                    } else {
                        Image image;
                        try {
                            BufferedImage bufimage = ImageIO.read(files[0]);
                            image = SwingFXUtils.toFXImage(bufimage, null);
                        } catch (IOException ioe) {
                            image = defaultimage;
                        }
                        view = new ImageView(image);
                    }
                }
                //adds name of application for each plugin.
                labels.add(new Label(appData.get(i).get(0)));
                gridPane.add(labels.get(i), 1, i);
                //adds icon of application for each plugin
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
     * Adding the appropriate files for each plugin to the plugin 2D Arraylist.
     */
    private void loadPlugins() {
        List<List<File>> packages = new ArrayList<>();
        //Adds the numbered folder that holds the plugins to the classpath.
        for (int i = 0; i < list.length; i ++) {
            try {
                AddPlugsController.addPath(list[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println(list[i]);
            File[] pack = list[i].listFiles();
            packages.add(new ArrayList());
            //goes the resources and numbered folders to an arraylist
            for (int j = 0; j < pack.length; j++) {
                packages.get(i).add(pack[j]);
            }
        }
        //System.out.println(packages.get(0));
        //This goes through each plugin folder looking for preview folder, icon folder, app.txt file
        //and jar file for each plugin.
        int k = 0;
        for (int i = 0; i < packages.size(); i++) {
            Iterator iterator = packages.get(i).iterator();
            plugins.add(new ArrayList());
            preview.add(new ArrayList());

            //Creates nonexisting folder if no preview folder in first and only plugin
            if (k > 0 && (preview.get(i).size() < plugins.size())) {
                preview.get(0).add(new File(""));
            }
            while (iterator.hasNext()) {
                File file = (File) iterator.next();
                if ("preview".equals(file.getName().toLowerCase())) {
                    preview.get(i).add(file);
                } else if ("icon".equals(file.getName().toLowerCase())) {
                    icons.add(file);
                } else if ("app.txt".equals(file.getName().toLowerCase())) {
                    appTxt = file;
                } else if ("fxml".equals(file.getName().toLowerCase())) {
                    File[] fxmlFiles = file.listFiles();
                    for (int j = 0; j < fxmlFiles.length; j++) {
                        if ("main.fxml".equals(fxmlFiles[j].getName().toLowerCase())) {
                            plugins.get(i).add(fxmlFiles[j]);
                        }
                    }
                } else {
                    String[] string = file.getName().split("\\.");
                    if (string.length > 1 && "jar".equals(string[1])) {
                        plugins.get(i).add(file);
                    }
                }
            }
                k++;
            try {
                String[] data = readAppTxt();
                appData.add(new ArrayList());
                appData.get(i).add(data[0]);
                appData.get(i).add(data[1]);
            } catch (Exception e) {
                System.out.println(i + " app.txt file could not be found");
            }
        }
        //Creates nonexisting folder if no preview folder in first and only plugin
        if ((preview.get(0).size() < plugins.size()) && (plugins.size() == 1)) {

            preview.get(0).add(new File(""));
        }
        /**
         * Get Case No Preview folder in last added plugin
         */
        //if ((preview.get(0).size() < plugins.size()) && (plugins.size() > 1)) {
                //System.out.println(new File(""));
           // preview.get(0).add(new File(""));
        //}



    }


    public String[] readAppTxt() throws Exception {
        BufferedReader bufReader = new BufferedReader(new FileReader(appTxt));
        String line = bufReader.readLine();
        line = line.trim();
        String[] appData = new String[2];
        int i = 0;
        while (line != null && i < 2) {
            appData[i] = line;
            i++;
            line = bufReader.readLine();
        }
        return appData;
    }

    /**
     * A static method to get the files of the chosen plugin.
     * @return  File[] the chosen plugin.
     */
    public static File[] getChosenPlugin() {
        return chosenPlugin;
    }


    public static String getDescription() {
        return description;
    }

    public static File getPreview() {
        return chosenPreview;
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