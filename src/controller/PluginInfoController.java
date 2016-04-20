package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utilities.JarResources;
import utilities.MultiClassLoader;
import utilities.NextScreen;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
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

    /** the fxml to be loaded for the first plugin's screen. */
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

    /** label containing the description */
    @FXML
    private Label descripLabel;

    /** Gestures object */
    private Gestures gestures;

    /** The current index of the plugin within the array */
    private int index;

    /** The Contains the Name/Description of the plugin */
    private File[] plugin;

    /** Contains the number of the plugins installed */
    private int numOfPlugins;

    /** Eventhandler */
    private EventHandler<MouseEvent> handler1;

    /** Eventhandler */
    private EventHandler<MouseEvent> handler2;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	System.out.println("WHATTT");
    	MainScreen.addObserver(this);
        
        gestures = new Gestures();
        index = MainController.getCurIndex();
        plugin = MainController.getChosenPlugin();
        numOfPlugins = MainController.getNumOfPlugins();
        dialog = new Dialog<>();
        close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        plugin = MainController.getChosenPlugin(index);
        pictures = MainController.getPreview(index);
        descripLabel.setText(MainController.getDescription(index));
        setFiles();
        setSlideShow();
        setEvents();
        try {
			MainController.addPath(MainController.getList()[index]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        ClassLoader cl = ClassLoader.getSystemClassLoader();
//        URL[] urls = ((URLClassLoader)cl).getURLs();
//        for(URL url: urls){
//        System.out.println("BACK LIKE BLACK" + url.getFile());
//            //System.out.println("ADSS " + url.getFile().toString());
//        }
        //System.out.println(fxml.getAbsolutePath());
        //System.out.println(index);
        //System.out.println(numOfPlugins);
        handler1 = event -> gestures.mouseEntered(event.getX(), event.getY());
        handler2 = event -> {
            int horzValue = gestures.horizontalSwipe(event.getX(), event.getY());
            //System.out.println("HIII");
            if (horzValue == 1) {
                if (index != 0) {
                    //System.out.println("Left to Right Swipe");
                    goToBackPlugin(index);
                }
            } else if (horzValue == 2) {
                if ((numOfPlugins != (index+1))) {
                    //System.out.println("Right to Left Swipe");
                    goToNextPlugin(index);
                }
            }
        };
        //System.out.println("VISSS");
        MainScreen.getCurrentStage().addEventHandler(MouseEvent.MOUSE_PRESSED, handler1);
        MainScreen.getCurrentStage().addEventHandler(MouseEvent.MOUSE_RELEASED, handler2);
        //System.out.println("HI");
    }

    /**
     * Going to the previous plugin.
     * @param index
     */
    private void goToBackPlugin(int index) {
        int i = index - 1;
            MainController.setCurIndex(i);
            MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_PRESSED, handler1);
            MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_RELEASED, handler2);
        try {
            //MainController.removePath(MainController.getList()[index]);
           //MainController.addPath(MainController.getList()[i]);
            

            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
           goToNextScreen("/fxml/PluginInfo.fxml");
    }

    /**
     * Going to the next plugin.
     * @param index
     */
    private void goToNextPlugin(int index) {
        int i = index + 1;
            MainController.setCurIndex(i);
            MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_PRESSED, handler1);
            MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_RELEASED, handler2);
        try {
        	//MainController.removePath(MainController.getList()[index]); 
//          
//            ClassLoader cl = ClassLoader.getSystemClassLoader();
//            URL[] urls = ((URLClassLoader)cl).getURLs();
//            for(URL url: urls){
//            System.out.println(url.getFile());
//                //System.out.println("ADSS " + url.getFile().toString());
//            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
            goToNextScreen("/fxml/PluginInfo.fxml");
    }

    /**
     * Sets the event of when the slideshow is clicked, the plugin in be loaded.
     */
    private void setEvents() {
        stackPane.setOnMouseClicked(event -> {
//        	ClassLoader cl = ClassLoader.getSystemClassLoader();
//
//            URL[] urls = ((URLClassLoader)cl).getURLs();
//
//            for(URL url: urls){
//                System.out.println("HI" + url.getFile());
//                //System.out.println("ADSS " + url.getFile().toString());
//            }
            JarClassLoader jarLoader = new JarClassLoader(jarFile.getAbsolutePath());
            Object object = null;
            try {
                Class c = jarLoader.loadClass("controller.Main", true);
                object = c.newInstance();
            } catch (Exception e) {
                dialog("Error", "Plugin can not be loaded.");
            }
            
            App app = (App) object;
            //app.setTitle("Name");
            //String[] string = fxml.getAbsolutePath().split("/home/cjwest");
            //String[] string = fxml.getAbsolutePath().split("/home/cjwest/resources");
            //String[] string = fxml.getAbsolutePath().split("/home/touchmeister/resources");
            String[] string = fxml.getAbsolutePath().split("/home/touchmeister");
            System.out.println(string[0]);
            System.out.println(string[1]);
            //app.setFxmlAndTitle(string[1], "NAMEEE");

            //app.setTitle("HII");a
            //app.setFxml(string[1]);
            //System.out.println(app.);
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
                                System.out.println("CLOSE");
                                stage.close();
                                MainScreen.getCurrentStage().toFront();
                                MainScreen.getCurrentStage().setFullScreen(true);
                            }
                        }
                    });
                    stage.setOnCloseRequest((windowEvent) -> {
                        MainScreen.getCurrentStage().toFront();
                        MainScreen.getCurrentStage().setFullScreen(true);
                    });
                    MainScreen.getCurrentStage().toBack();
                   //System.out.println(this.getClass().getResource(string[1]).toString());
                    //System.out.println("OMG");
                    
                    //Works for jar file
                  //app.goToScreen(stage, "HII", "/resources/1/fxml/main.fxml");
                    
                   app.setFxmlAndTitle(string[1], "Capstone");
                    //app.setFxml(this.getClass().getResource(string[1]).toString());
                    //app.setMainStage(MainScreen.getStage());
                   //MainScreen.getTimeline().stop();
                   //MainScreen.getCurrentStage().removeEventHandler(MouseEvent.ANY, MainScreen.getEvent());
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
        stackPane.getChildren().add(pics.get(0));
        //System.out.println("AAAAA");
        /**
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
         */
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
        images[0] = image1;
        return images;
    }

    /**
     * Set the main and the fxml from the chosenPlugin array.
     */
    private void setFiles() {
        //System.out.println(plugin[0].getName());
        //System.out.println(plugin[1].getName());
        String[] string = plugin[0].getName().split("\\.");
        //System.out.println(string[0]);
        String first = string[0].trim();

        //System.out.println(first);
        if (first.toLowerCase().equals("main")) {
            jarFile = plugin[1];
            fxml = plugin[0];
            //System.out.println(fxml.getAbsolutePath());
        } else {
            jarFile = plugin[0];
            fxml = plugin[1];
            //System.out.println("FDSSS" + jarFile.getAbsolutePath());
            //System.out.println("FDS" + fxml.getAbsolutePath());
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
        MainScreen.removeObserver(this);
        try {
            MainController.removePath(MainController.getList()[index]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        goToNextScreen("/fxml/Main.fxml");
    }
}