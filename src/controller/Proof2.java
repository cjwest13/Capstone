package controller;

import controller.MainScreen;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.JarResources;
import utilities.MultiClassLoader;
import utilities.keyb.demo.VKDemo;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller for the AddPlugs.fxml file.
 * @author  Clifton West
 * @version October 4, 2015
 */
public class Proof2 implements Initializable {

    /** Dialog popup box */
    Dialog<String> dialog;

    /** Close Button for the Dialog box */

    private ButtonType close;

    @FXML
    private Button clos;

    /** The file selected */
    //File file;


    /** Label representing the label containing the time in the fxml */
    @FXML
    private Label timeLbl;

    @FXML
    TextField txt;

    @FXML
    AnchorPane anchorPane;

    @FXML
    GridPane MidGrid;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dialog = new Dialog<>();
        close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        time();
        txt.setText("OMG");
        VKDemo vkDemo = new VKDemo();
        vkDemo.start(MainScreen.getStage());
    }

    /**
     * Animation to show the time.
     */
    private void time() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), event -> {
            //timeLbl.setText(getDateAndTime());
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    /**
     * Proof to demostrate the creation on an object
     */
    @FXML
    public void playApp() {
        String resourceDir = "/home/cjwest/resources";
        String number = "" + 1;
        File destDir = new File(resourceDir, number);
        try {
            AddPlugsController.addPath(destDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JarClassLoader jarLoader = new JarClassLoader("/home/cjwest/resources/1/new1.jar");

        Object object = null;
        try {
            Class c = jarLoader.loadClass("controller.Main", true);
            object = c.newInstance();
        } catch (ClassNotFoundException cnfe) {
            System.out.println("bruuuhh");
        } catch (InstantiationException ie) {
            System.out.println("howw");
        } catch (IllegalAccessException ie) {
            System.out.println("Illl");
        }

        Method[] methods = object.getClass().getMethods();
        for(Method method : methods) {
            System.out.println("method = " + method.getName());
        }
        System.out.println(object.getClass().getSuperclass().toString());
        System.out.println(object instanceof App);
        System.out.println(object instanceof Application);
        System.out.println(object instanceof Object);


        App app = (App) object;
        //app.doSomething();
        //app.doanything();
        //System.out.println(app.getFXML());
        //System.out.println(app.getTitle());
        //app.setFXML("/1/fxml/main.fxml");
        //System.out.println(app.getFXML());

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        app.start(new Stage());
                    } catch (Exception ioe) {
                        ioe.printStackTrace();
                    }
                }
            });
        } catch (Exception ioe) {
            ioe.printStackTrace();
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
    public void close() {
        Platform.exit();
    }

    public class JarClassLoader extends MultiClassLoader {
        private JarResources jarResources;
        private String jarName;


        public JarClassLoader(String jarName) {
            jarResources = new JarResources (jarName);
        }

        protected byte[] loadClassBytes (String className) {
            // Support the MultiClassLoader's class name munging facility.
            className = formatClassName (className);
            // Attempt to get the class data from the JarResource.
            return (jarResources.getResource (className));
        }
    }

}