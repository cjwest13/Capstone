package controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.JarResources;
import utilities.MainOb.MainControl;
import utilities.MultiClassLoader;
import utilities.NextScreen;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller for the PluginInfo.fxml file.
 * @author  Clifton West
 * @version October 3, 2015
 */
public class PluginInfoController implements Observer, Initializable, NextScreen {

    /** StackPane representing the stackPane in the fxml */
    @FXML
    private StackPane stackPane;

    /** File that will contain the preview folder */
    private File pictures;

    /**
     * the fxml to be loaded for the first plugin's scrren.
     */
    private File fxml;

    /** The path to the jarFile */
    private File jarFile;

    /** Label representing the label containing the time in the fxml */
    @FXML
    private Label timeLbl;

    /** Dialog popup box */
    Dialog<String> dialog;

    /** Close Button for the Dialog box */
    private ButtonType close;

    @FXML
    private Label descripLabel;

    private Gestures gestures = new Gestures();

    private int horzValue;

    private int vertValue;

    private int index;

    private File[] plugin;

    private int numOfPlugins;

    private EventHandler<MouseEvent> handler1;

    private EventHandler<MouseEvent> handler2;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        index = MainController.getCurIndex();
        plugin = MainController.getChosenPlugin();
        numOfPlugins = MainController.getNumOfPlugins();
        System.out.println(index);
        System.out.println(numOfPlugins);
        handler1 = event -> gestures.mouseEntered(event.getX(), event.getY());
        handler2 = event -> {
            horzValue = gestures.horizontalSwipe(event.getX(), event.getY());
            if (horzValue == 1) {
                System.out.println("Left to Right Swipe");
                goToBackPlugin(index);
            } else if (horzValue == 2) {
                System.out.println("Right to Left Swipe");
                goToNextPlugin(index);
            }
        };
        MainScreen.getStage().addEventHandler(MouseEvent.MOUSE_PRESSED, handler1);
        MainScreen.getStage().addEventHandler(MouseEvent.MOUSE_RELEASED, handler2);
        MainScreen.addNewObserver(this);
        dialog = new Dialog<>();
        close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        plugin = MainController.getChosenPlugin(index);
        pictures = MainController.getPreview(index);
        descripLabel.setText(MainController.getDescription(index));
        setFiles();
        setSlideShow();
        setEvents();

    }

    /**
     * Going to the previous plugin.
     * @param index
     */
    private void goToBackPlugin(int index) {
        int i = index - 1;
        //System.out.println("old index " + index);
        if ( index > 0) {
            //System.out.println("new index " + i);
            MainController.setCurIndex(i);
            MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_PRESSED, handler1);
            MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_RELEASED, handler2);
            goToScreenSaver("/fxml/PluginInfo.fxml");
        }
    }

    /**
     * Going to the next plugin.
     * @param index
     */
    private void goToNextPlugin(int index) {
        int i = index + 1;
        //System.out.println("cur index " + index);
        //System.out.println("new index " + i);
        if (numOfPlugins != i) {
            MainController.setCurIndex(i);
            MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_PRESSED, handler1);
            MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_RELEASED, handler2);
            goToScreenSaver("/fxml/PluginInfo.fxml");
        }
    }

    /**
     * Sets the event of when the slideshow is clicked, the plugin in be loaded.
     */
    private void setEvents() {
        stackPane.setOnMouseClicked(event -> {
            JarClassLoader jarLoader = new JarClassLoader(jarFile.getAbsolutePath());
            Object object = null;
            try {
                Class c = jarLoader.loadClass("controller.Main", true);
                object = c.newInstance();
            } catch (Exception e) {
                dialog("Error", "Plugin can not be loaded.");
            }
            
            App app = (App) object;
            app.setTitle("Name");
            String[] string = fxml.getAbsolutePath().split("/home/cjwest/resources");
            System.out.println(string[0]);
            app.setFxml(string[1]);
            Platform.runLater(() -> {
                try {
                    Stage stage = new Stage();
                    stage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            //System.out.println("OMG " + newValue);
                        }
                    });
                    stage.showingProperty().addListener(new ChangeListener<Boolean>() {
                        int i = 0;
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            System.out.println("DSDDS " + i);
                            i++;
                            if (i % 2 == 0) {
                                System.out.println("DSDDS");
                                stage.close();
                                MainScreen.getStage().toFront();
                                MainScreen.getStage().setFullScreen(true);
                            }
                        }
                    });
                    stage.setOnCloseRequest((windowEvent) -> {
                        MainScreen.getStage().toFront();
                        MainScreen.getStage().setFullScreen(true);
                    });
                    MainScreen.getStage().toBack();
                    //app.setMainStage(MainScreen.getStage());
                    app.start(stage);

                } catch (Exception ioe) {
                    ioe.printStackTrace();
                }
            });
        });
    }

    /**
     * Setting the slideshow of the previews.
     */
    private void setSlideShow() {
        ArrayList<ImageView> pics = new ArrayList();
        pics = addImages();
        //System.out.println("AAAAA");
        SequentialTransition slideshow = new SequentialTransition();
        for (ImageView slide : pics) {

            //System.out.println("AAAAA");
            SequentialTransition transition = new SequentialTransition();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), slide);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            PauseTransition stayOn = new PauseTransition(Duration.millis(2000));
            FadeTransition fadeOut = new FadeTransition(Duration.millis(2000), slide);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            transition.getChildren().addAll(fadeIn, stayOn, fadeOut);
            slide.setOpacity(0);
            stackPane.getChildren().add(slide);
            slideshow.getChildren().add(transition);
        }
        slideshow.setCycleCount(Timeline.INDEFINITE);
        slideshow.play();
    }

    /**
     * Adding the previews to the pics arraylist.
     */
    private ArrayList<ImageView> addImages() {
        ArrayList<ImageView> pics = new ArrayList();

        if (pictures.exists()) {
            File[] files = pictures.listFiles();
            if (files.length != 0) {
                try {
                    for (File file : files) {
                        BufferedImage bufimage = ImageIO.read(file);
                        Image image = SwingFXUtils.toFXImage(bufimage, null);
                        ImageView view = new ImageView(image);
                        view.setFitHeight(430);
                        view.setPreserveRatio(true);
                        pics.add(view);
                    }
                } catch (IOException ioe) {}
                return pics;
            }
        }

        Image[] images = defaultImages();
        for (Image image : images) {
            ImageView view = new ImageView(image);
            view.setFitHeight(430);
            view.setPreserveRatio(true);
            pics.add(view);
        }
        return pics;
    }

    /**
     * Creates an Image Array of the default images for a preview when necessary.
     * @return Image[] of default images.
     */
    private Image[] defaultImages() {
        Image[] images = new Image[6];
        Image image1 = new Image("/utilities/defaultPreviews/one.jpg");
        Image image2 = new Image("/utilities/defaultPreviews/two.jpg");
        Image image3 = new Image("/utilities/defaultPreviews/three.jpg");
        Image image4 = new Image("/utilities/defaultPreviews/four.jpg");
        Image image5 = new Image("/utilities/defaultPreviews/five.jpg");
        Image image6 = new Image("/utilities/defaultPreviews/six.jpg");
        images[0] = image1;
        images[1] = image2;
        images[2] = image3;
        images[3] = image4;
        images[4] = image5;
        images[5] = image6;
        return images;
    }

    /**
     * Set the jar file and the preview folder from the chosenPlugin array.
     */
    private void setFiles() {
        //File[] plugin = MainController.getChosenPlugin();
        //pictures = MainController.getPreview();
        String[] string = plugin[0].getName().split("\\.");
        if (string.length > 1) {
            jarFile = plugin[0];
            fxml = plugin[1];
        } else {
            jarFile = plugin[1];
            fxml = plugin[0];
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
     * Sets label to the current time.
     * @param date  Date object that is passed.
     */
    @Override
    public void update(Date date) {
        //System.out.println();
        //System.out.println(date.toString());
        timeLbl.setText(date.toString());
    }

    /**
     * Inner class to load the Class from the Jar File
     */
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

    /**
     * Function assigned to a fxml button that goes to the Main.fxml screen.
     */
    @FXML
    public void goToMain() {
        goToNextScreen("/fxml/Main.fxml");
    }
}