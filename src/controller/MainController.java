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
import javafx.scene.web.WebView;
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
public class MainController implements Initializable, NextScreen, Observer {
    /** Label representing a label in the fxml */

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

    /** File of appTxt */
    private File appTxt;

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

    /** Arraylist of the icon files */
    //private Web webView = new Web();

    /** Image representing the default image */
    private Image defaultimage;

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
        System.out.println("HSD");
        addPlugins();

        setEvents();
        changeGreeting(greeting);

        /** Sets the settings button to be visible on key pressed */
        anchorPane.setOnKeyPressed(ke-> {
            if (ke.getCode().equals(KeyCode.K)) {
                settingbtn.setVisible(true);
            }
        });
        //ImageView imageView = null;
        //WebView broswer = null;
        //try {
            //imageView = webView.getWebHtml("https://cdn1.iconfinder.com/data/icons/logotypes/32/twitter-128.png");
            //broswer = webView.getWebHtml("https://www.google.com");
        //} catch (Exception e) {}
        //labels.add(new Label(imageView));
        //gridPane.add(imageView, 1, 0);
        //gridPane.add(broswer, 1, 0);
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
    }

    /**
     * Method use to edit a plugin's information.
     * @param index
     * @param name
     * @param data
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
     * @return
     */
    public static List<List<File>> getPlugins() {
        return packages;
    }

    /**
     * Gets all of the icons.
     * @return
     */
    public static ArrayList<ImageView> getIcons() {
        return iconsView;
    }

    /**
     * Gets the current index.
     * @return
     */
    public static int getCurIndex() {
        return curIndex;
    }

    /**
     * Sets the current index.
     * @param i
     */
    public static void setCurIndex(int i) {
        curIndex = i;
    }

    /**
     * Gets all of the appData.
     * @return
     */
    public static List<List<String>> getAppNames() {
        //if (change) {
         //   return newAppData;
        //} else {
            return appData;
       // }
    }

    /**
     * Adds the icon and name of application to the grid.
     */
    private void addPlugins() {
        if (isPlugins()) {
            loadPlugins();
            //Default Icon

            //Going through the plugin 2d Arraylist
            for (int i = 0; i < plugins.size(); i++) {
                File files;
                ImageView view;
                //check to see if there are enough icons in the icons array
                /**
                 * FIX CASE (TURN ICONS IN 2D ARRAYLIST SOON)  (ITS FIXED)!!!
                 */
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
                view.setFitHeight(150);
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
            System.out.println("AHH");
            Iterator iterator = packages.get(i).iterator();
            plugins.add(new ArrayList());
            preview.add(new ArrayList());
            icons.add(new ArrayList());

            //Creates nonexisting folder if no preview folder in first and only plugin
            if (k > 0 && (preview.get(i).size() < plugins.size())) {
                preview.get(i).add(new File(""));
            }
            //if (icons.size() < i || (icons.size() == i && i == 0)) {
            if (k > 0 && (icons.get(i).size() < plugins.size())) {
                icons.get(k -1).add(new File(""));
                //File file = new File("/utilities/cone.png");
                //icons.get(k - 1).add(file);
               // view = new ImageView(defaultimage);
            }
            while (iterator.hasNext()) {
                File file = (File) iterator.next();
                if ("preview".equals(file.getName().toLowerCase())) {
                    preview.get(i).add(file);
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
                /**GET CASE IF ITS NOT APPDATA FILE*/
                String[] data = readAppTxt();
                appData.add(new ArrayList());
                if (!change) {
                    appData.get(i).add(data[0]);
                    appData.get(i).add(data[1]);
                } else {
                    appData.get(i).add(newAppData.get(i).get(0));
                    appData.get(i).add(newAppData.get(i).get(1));
                }
                //appData.get(i).add(data[0]);
               // appData.get(i).add(data[1]);
            } catch (Exception e) {
                System.out.println(i + " app.txt file could not be found");
            }
        }
        //Creates nonexisting folder if no preview folder in first and only plugin
        if ((preview.get(0).size() < plugins.size()) && (plugins.size() == 1)) {
            preview.get(0).add(new File(""));
        }

        if ((icons.get(0).size() < plugins.size()) && (plugins.size() == 1)) {

            icons.get(0).add(new File("/utilities/cone.png"));
        }
        //System.out.println(icons.get(0).get(0).getAbsolutePath());
        //System.out.println(icons.get(1).get(0).getAbsolutePath());
        /**
         * Get Case No Preview folder & Icon Folder in last added plugin
         */
        //if ((preview.get(0).size() < plugins.size()) && (plugins.size() > 1)) {
                //System.out.println(new File(""));
           // preview.get(0).add(new File(""));
        //}



    }

    /**
     * Readss akk
     * @return
     * @throws Exception
     */
    public String[] readAppTxt() throws Exception {
        BufferedReader bufReader = new BufferedReader(new FileReader(appTxt));
        StringBuilder stringBuilder = new StringBuilder();
        //String line = bufReader.readLine();
        //line = line.trim();
        String[] appData = new String[2];
        int i = 0;
        String line;
        while ((line = bufReader.readLine()) != null) {
            if (i == 0) {
                appData[0] = line;
            } else {
                stringBuilder.append(line);
            }
            i++;
            //line = bufReader.readLine();
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
    public static File[] getChosenPlugin(int i) {
        chosenPlugin[0] = plugins.get(i).get(0);
        chosenPlugin[1] = plugins.get(i).get(1);
        return chosenPlugin;
    }
    public static String getDescription(int i) {
        description = appData.get(i).get(1);
        return description;
    }

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
    //@FXML
    //public void goToSettings() {
       //NextScreen.super.goToNextScreen("/fxml/Authorization.fxml");
   // }

    @FXML
    public void goToSettings() {
        NextScreen.super.goToNextScreen("/fxml/Settings.fxml");
    }

    /**
     * Shows the time
     * @param date
     */
    @Override
    public void update(Date date) {
        timeLbl.setText(date.toString());
    }
}