package project;

import javafx.event.EventHandler;
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
import utilities.NextScreen;
import java.io.File;
import java.net.URL;
import java.util.*;

import javafx.scene.input.MouseEvent;
/**
 * Controller class for Main.fxml.
 * @author  Clifton West, John Burrell
 * @version October 3, 2015
 */
public class MainController implements Initializable, NextScreen {
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

    /** String containing the greeting. */
    public static String greeting;

    @FXML
    private GridPane gridPane;

    private File[] list;

    private List<List<File>> plugins;

    private ArrayList<File> icons;

    private static File[] chosenPlugin;

    private static int numOfPlugins;

    private ArrayList<Label> labels;

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
        icons= new ArrayList();
        addPlugins();
        setEvents();
        //j = 0;
        changeGreeting(greeting);

        /** Sets the settings button to be visible */
        anchorPane.setOnKeyPressed(ke-> {
            if (ke.getCode().equals(KeyCode.K)) {
                settingbtn.setVisible(true);
            }
        });
    }
    private void setEvents() {
        for (Label label: labels) {
            label.setOnMouseClicked(event ->  {
                int index = gridPane.getRowIndex(label);
                chosenPlugin[0] = plugins.get(index).get(0);
                chosenPlugin[1] = plugins.get(index).get(1);
                goToNextScreen("/fxml/PluginInfo.fxml");
            });
        }
    }

    private void addPlugins() {
        if (isPlugins()) {
            loadPlugins();
            Image image = new Image("./utilities/cone.png");
            //dynamically adding
            for (int i = 0; i < plugins.size(); i++) {
                File[] files;
                ImageView view;
                if (icons.size() <= i) {
                    view = new ImageView(image);
                } else {
                    files = icons.get(i).listFiles();
                    if (files.length == 0) {
                        view = new ImageView(image);
                    } else {
                        view = new ImageView("/1/icon/Robot_icon.png");
                    }
                }
                labels.add(new Label("omg"+i));
                gridPane.add(labels.get(i), 1, i);

                view.setFitHeight(150);
                view.setPreserveRatio(true);
                gridPane.add(view, 0, i);
                System.out.println("nooo");

            }
        }
    }

    private Boolean isPlugins() {
        File file = new File("/home/cjwest/resources");
        list = file.listFiles();
        numOfPlugins = list.length;
        if (list.length == 0) {
            return false;
        } else {
            return true;
        }
    }

    private void loadPlugins() {
        List<List<File>> packages = new ArrayList<>();
        //going through each plugin

        for (int i = 0; i < list.length; i ++) {
            File[] pack = list[i].listFiles();
            packages.add(new ArrayList());
            //System.out.println("Stuff in Packages");
            for (int j = 0; j < pack.length; j++) {
                packages.get(i).add(pack[j]);
            }
        }
        //looking for pictures folder and jar file.
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
        System.out.println(plugins.get(0).toString());
        System.out.println(plugins.get(1).toString());
    }

    public static File[] getChosenPlugin() {
       return chosenPlugin;
    }

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
            //System.out.println(greeting);
            //System.out.println(text);
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
