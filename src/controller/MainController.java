package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import utilities.NextScreen;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Controller class for Main.fxml.
 * @author  Clifton West
 * @version October 3, 2015
 */
public class MainController implements Initializable, NextScreen, Observer {
    /** AnchorPane representing the anchorpane in the fxml */
    @FXML
    private AnchorPane anchorPane;
    
    /** BUtton representing the settings button in the fxml */
    @FXML
    private Button settingbtn;
    
    /** Label representing the greeting label in the fxml */
    @FXML
    private Label topLabel;

    /** GridPane representing the center grid in the fxml */
    @FXML
    private GridPane gridPane;

    /** Label representing the label containing the time in the fxml */
    @FXML
    private Label timeLbl;

    /** String containing the greeting. */
    public static String greeting;

    /** 2D Arraylist containing the jar file, and preview folder for each plugin */
    private static List<List<File>> plugins;

    /** 2D Arraylist of the icon files */
    private static List<List<File>> icons;

    /** 2D Arraylist of the icons as ImageViews */
    private static ArrayList<ImageView> iconsView;

    /** 2D Arraylist of the appData */
    private static List<List<String>> appData;

    /** 2D Arraylist of the newAppData */
    private static List<List<String>> newAppData;

    /** A file array containing the chosen plugin's jar file and preview folder */
    private static File[] chosenPlugin;

    /** the number of plugins */
    private static int numOfPlugins;

    /** An arraylist of the labels in the grid */
    private ArrayList<Label> labels;

    /** 2D Arraylist of the preview files */
    private static List<List<File>> preview;

    /** File representing the chosenPreview */
    private static File chosenPreview;

    /** String represents the description */
    private static String description;

    /** 2D Arraylist of the packages files */
    private static List<List<File>> packages;

    /** Arraylist of the icon files */
    private static Boolean change;

    /** Int representing the current index */
    private static int curIndex;

    /** Image representing the default image */
    private Image defaultimage;

    /** An File array containing the folders for each plugin */
    static File[] list;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainScreen.addObserver(this);
        chosenPlugin = new File[2];
        defaultimage = new Image("/utilities/cone.png");
        appData = new ArrayList();
        plugins = new ArrayList();
        labels = new ArrayList();
        icons = new ArrayList();
        preview = new ArrayList();
        iconsView = new ArrayList();
        if (change == null || !change) {
            newAppData = appData;
            change = false;
        }
        addPlugins();
        setEvents();
        changeGreeting(greeting);
        /** Sets the settings button to be visible on key pressed */
        anchorPane.setOnKeyPressed(ke-> {
            if (ke.getCode().equals(KeyCode.K)) {
                settingbtn.setVisible(true);
            }
        });
    }

    /**
     * Sets the events for each label in the grid.
     */
    private void setEvents() {
        for (Label label: labels) {
            label.setOnMouseClicked(event ->  {
                int index = gridPane.getRowIndex(label);
                chosenPlugin[0] = plugins.get(index).get(0);
                curIndex = index;
                chosenPlugin[1] = plugins.get(index).get(1);
                chosenPreview = preview.get(index).get(0);
                description = appData.get(index).get(1);
                goToNextScreen("/fxml/PluginInfo.fxml");
            });
        }
        for (ImageView image: iconsView) {
        	image.setOnMouseClicked(event -> {
        		int index = gridPane.getRowIndex(image);
                chosenPlugin[0] = plugins.get(index).get(0);
                curIndex = index;
                chosenPlugin[1] = plugins.get(index).get(1);
                chosenPreview = preview.get(index).get(0);
                description = appData.get(index).get(1);
                goToNextScreen("/fxml/PluginInfo.fxml");
        	});
        }
    }

    /**
     * Method use to edit a plugin's information.
     * @param index Index of plugin in the arraylist.
     * @param name  If the name was changed. If not, the description was changed.
     * @param data  The string containing the updated data.
     */
    public static void changeAppData(int index, Boolean name, String data) {
        if (name) {
            newAppData.get(index).add(0, data);
            newAppData.get(index).remove(1);
        } else {
            newAppData.get(index).add(1, data);
            newAppData.get(index).remove(2);
        }
        change = true;
    }

    /**
     * Gets all of the plugins
     * @return 2D List containing the current plugins.
     */
    public static List<List<File>> getPlugins() {
        return packages;
    }

    /**
     * Gets all the numbers folders.
     * @return list containing the numbered folders.
     */
    public static File[] getList() {
        return list;
    }

    /**
     * Gets all of the icons.
     * @return Arraylist of the icons.
     */
    public static ArrayList<ImageView> getIcons() {
        return iconsView;
    }

    /**
     * Gets the current index.
     * @return The index of the selected plugin.
     */
    public static int getCurIndex() {
        return curIndex;
    }

    /**
     * Sets the current index.
     * @param i setting the index of the plugin selected.
     */
    public static void setCurIndex(int i) {
        curIndex = i;
    }

    /**
     * Gets all of the appData for the plugins.
     * @return 2D List containing the information for the plugins.
     */
    public static List<List<String>> getAppNames() {
        return appData;
    }

    /**
     * Adds the icon and name of application to the grid.
     */
    private void addPlugins() {
        if (isPlugins()) {
            loadPlugins();
            //Going through the plugin 2d Arraylist
            for (int i = 0; i < plugins.size(); i++) {
                File files;
                ImageView view;
                files = icons.get(i).get(0);
                if (!files.exists()) {
                    view = new ImageView(defaultimage);

                } else {
                    File[] listFiles = files.listFiles();
                    File file = listFiles[0];
                    Image image;
                    try {
                        BufferedImage bufimage = ImageIO.read(file);
                        image = SwingFXUtils.toFXImage(bufimage, null);
                    } catch (IOException ioe) {
                        image = defaultimage;
                    }
                    view = new ImageView(image);

                }
                //adds name of application for each plugin.
                if (!change) {
                    labels.add(new Label(newAppData.get(i).get(0)));
                } else {
                    labels.add(new Label(appData.get(i).get(0)));
                }
                gridPane.add(labels.get(i), 1, i);
                //adds icon of application for each plugin
                view.setFitHeight(100);
                view.setPreserveRatio(true);
                iconsView.add(view);
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
        packages = new ArrayList<>();
        File appTxt = null;
        //Adds the numbered folder that holds the plugins to the classpath.
        for (int i = 0; i < list.length; i ++) {
            File[] pack = list[i].listFiles();
            packages.add(new ArrayList());
            //goes the resources and numbered folders to an arraylist
            for (int j = 0; j < pack.length; j++) {
                packages.get(i).add(pack[j]);
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //This goes through each plugin folder looking for preview folder, icon folder, app.txt file
        //and jar file for each plugin.
        int k = 0;
        for (int i = 0; i < packages.size(); i++) {
            appTxt = null;
            Iterator iterator = packages.get(i).iterator();
            plugins.add(new ArrayList());
            preview.add(new ArrayList());
            icons.add(new ArrayList());

            //Creates nonexisting folder if no preview folder in first and only plugin
            if (k > 0 && (preview.size() < plugins.size())) {
                preview.get(i).add(new File(""));
            }
            //if (icons.size() < i || (icons.size() == i && i == 0)) {
            if (k > 0 && (icons.size() < plugins.size())) {
                icons.get(k-1).add(new File(""));
            }
            while (iterator.hasNext()) {
                File file = (File) iterator.next();
                if ("preview".equals(file.getName().toLowerCase())) {
                    preview.get(i).add(file);
                    //System.out.println(file.toString());
                } else if ("icon".equals(file.getName().toLowerCase())) {
                    icons.get(i).add(file);
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
                //Checking for AppData File
                if (appTxt != null) {
                    String[] data = readAppTxt(appTxt);
                    appData.add(new ArrayList());
                    if (!change) {
                        if (data.length == 0) {
                            appData.get(i).add("Name of Application " + i);
                            appData.get(i).add("Description of Application " + i);
                        } else if (data.length == 1) {
                            appData.get(i).add(data[0]);
                            appData.get(i).add("Description of Application " + i);
                        } else {
                            appData.get(i).add(data[0]);
                            appData.get(i).add(data[1]);
                        }
                    } else {
                        appData.get(i).add(newAppData.get(i).get(0));
                        appData.get(i).add(newAppData.get(i).get(1));
                    }
                } else {
                    appData.get(i).add("Name of Application " + i);
                    appData.get(i).add("Description of Application " + i);
                }
            }catch(Exception e){
                System.out.println(i + " :Something bad happen. ");
            }
        }
        /** Checking the last added plugin's icons/preview folders /*/
        Iterator iconIterator = icons.get(k-1).iterator();
        Iterator preIterator = preview.get(k-1).iterator();
        int iconCount=0;
        int preCount=0;
        while (iconIterator.hasNext()) {
            iconCount++;
            iconIterator.next();
        }
        while (preIterator.hasNext()) {
            preCount++;
            preIterator.next();
        }

        //Creates nonexisting folder if no preview folder in first and only plugin
        if ((preview.get(0).size() < plugins.size()) && (plugins.size() == 1)) {
            preview.get(0).add(new File(""));
            //Creates nonexisting folder if no preview folder in last plugin
        } else if (preCount == 0) {
            preview.get(k-1).add(new File(""));
        }
        //Creates nonexisting folder if no icon folder in first and only plugin
        if ((icons.get(0).size() < plugins.size()) && (plugins.size() == 1)) {
            icons.get(0).add(new File(""));
        }
        //Creates nonexisting folder if no icon folder in last plugin
        else if (iconCount == 0) {
            icons.get(k-1).add(new File(""));
        }
    }

    /**
     * Reads App.txt file and gets the name and description of the plugin.
     * @return String Array containing the name and description.
     * @throws Exception
     */
    public String[] readAppTxt(File appTxt) throws Exception {
        BufferedReader bufReader = new BufferedReader(new FileReader(appTxt));
        StringBuilder stringBuilder = new StringBuilder();
        String[] appData = new String[2];
        int i = 0;
        String line;
        while ((line = bufReader.readLine()) != null) {
            if (i == 0) {
                appData[0] = line;
            } else {
                stringBuilder.append(" " + line);
            }
            i++;
        }
        appData[1] = stringBuilder.toString();
        return appData;
    }

    /**
     * A static method to get the files of the chosen plugin.
     * @return  File[] the chosen plugin.
     */
    public static File[] getChosenPlugin() {
        return chosenPlugin;
    }

    /**
     * A static method to get the plugin chosen iva the index.
     * @param i index of the requested plugin.
     * @return File Array of the chosen plugin.
     */
    public static File[] getChosenPlugin(int i) {
        chosenPlugin[0] = plugins.get(i).get(0);
        chosenPlugin[1] = plugins.get(i).get(1);
        return chosenPlugin;
    }

    /**
     * A static method to get the description of a selected plugin.
     * @param i the plugin requested.
     * @return The String containing the description.
     */
    public static String getDescription(int i) {
        description = appData.get(i).get(1);
        return description;
    }

    /**
     * A Static method to get the preview of a selected plugin.
     * @param i the plugin requested.
     * @return The file of the plugin.
     */
    public static File getPreview(int i) {
        chosenPreview = preview.get(i).get(0);
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
            topLabel.setText("Hello!");
        } else {
            greeting = text;
            topLabel.setText(greeting);
        }
    }

    /**
     * Function assigned to a fxml button that goes to the Authorization.fxml screen.
     */
    @FXML
    public void goToSettings() {
        MainScreen.removeObserver(this);
        goToNextScreen("/fxml/Authorization.fxml");
    }

    /**
     * Shows the time via the observer pattern.
     * @param date Current date/time
     */
    @Override
    public void update(Date date) {
        timeLbl.setText(date.toString());
    }
}