package controller;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import utilities.JarResources;
import utilities.MultiClassLoader;
import utilities.NextScreen;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
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
    
    /** Confirmation Popbox */
    private Alert alert;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	alert = new Alert(AlertType.CONFIRMATION, "Do you wish to exit this plugin?", ButtonType.YES, ButtonType.NO);
    	alert.initModality(Modality.WINDOW_MODAL);
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
			addPath(MainController.getList()[index]);
		} catch (Exception e) {
			e.printStackTrace();
		}
        handler1 = event -> gestures.mouseEntered(event.getX(), event.getY());
        handler2 = event -> {
            int horzValue = gestures.horizontalSwipe(event.getX(), event.getY());
            if (horzValue == 1) {
                if (index != 0) {
                    goToBackPlugin(index);
                }
            } else if (horzValue == 2) {
                if ((numOfPlugins != (index+1))) {
                    goToNextPlugin(index);
                }
            }
        };
        MainScreen.getStage().addEventHandler(MouseEvent.MOUSE_PRESSED, handler1);
        MainScreen.getStage().addEventHandler(MouseEvent.MOUSE_RELEASED, handler2);
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
        goToNextScreen("/fxml/PluginInfo.fxml");
    }

    /**
     * Sets the event of when the slideshow is clicked, the plugin in be loaded.
     */
    private void setEvents() {
        stackPane.setOnMouseClicked(event -> {
        	MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_PRESSED, handler1);
            MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_RELEASED, handler2);
            JarClassLoader jarLoader = new JarClassLoader(jarFile.getAbsolutePath());
            Object object = null;
            try {
                Class c = jarLoader.loadClass("controller.Main", true);
                object = c.newInstance();
            } catch (Exception e) {
                dialog("Error", "Plugin can not be loaded.");
            }
            
            App app = (App) object;
            String[] string = fxml.getAbsolutePath().split("/home/cjwest");
            //String[] string = fxml.getAbsolutePath().split("/home/cjwest/resources");
            //String[] string = fxml.getAbsolutePath().split("/home/touchmeister/resources");
            //String[] string = fxml.getAbsolutePath().split("/home/touchmeister");
            EventHandler<MouseEvent> handler3 = event1 -> gestures.mouseEntered(event1.getX(), event1.getY());
            EventHandler<MouseEvent> handler4 = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					int value = gestures.diagonalSwipe(event.getX(), event.getY());
	            	if (value == 3) {
	            		alert.showAndWait();
	            		if (alert.getResult() == ButtonType.YES) {
	            			Sound sound = MainScreen.getSound();
	            			if (sound != null) {
	            				sound.endAllSounds();
	            			}
	            			
	            			MainScreen.getStage().setFullScreen(true);
	            			MainScreen.getStage().requestFocus();
	            			MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_PRESSED, handler3);
	            			MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_RELEASED, this);
	            			goToNextScreen("/fxml/PluginInfo.fxml");
	            			MainScreen.getStage().setFullScreen(true);
	            			
	            		}
	            	}					
				}
            };
            MainScreen.getStage().addEventHandler(MouseEvent.MOUSE_PRESSED, handler3);
            MainScreen.getStage().addEventHandler(MouseEvent.MOUSE_RELEASED, handler4);
            Platform.runLater(() -> {
                try { 
                	app.setFxmlAndTitle(string[1], "Capstone");
                	//System.out.println(string[1]);
                    app.start(MainScreen.getStage());
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
    }

    /**
     * Adding the previews to the pics arraylist.
     */
    private ArrayList<ImageView> addImages() {
        ArrayList<ImageView> pics = new ArrayList();
//        System.out.println(pictures.getAbsolutePath());
//        System.out.println(pictures.exists());
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
                //return pics;
            }
        } else { 
        Image[] images = defaultImages();
        ImageView view = new ImageView(images[0]);
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
        Image[] images = new Image[1];
        Image image1 = new Image("/utilities/defaultPreviews/one.jpg");
        images[0] = image1;
        return images;
    }

    /**
     * Set the main and the fxml from the chosenPlugin array.
     */
    private void setFiles() {
        String[] string = plugin[0].getName().split("\\.");
        String first = string[0].trim();
        if (first.toLowerCase().equals("main")) {
            jarFile = plugin[1];
            fxml = plugin[0];
        } else {
            jarFile = plugin[0];
            fxml = plugin[1];
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
        MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_PRESSED, handler1);
        MainScreen.getStage().removeEventHandler(MouseEvent.MOUSE_RELEASED, handler2);
        goToNextScreen("/fxml/Main.fxml");
    }
    
    /**
     * Adding file to the classpath.
     * @param f File
     * @throws Exception
     */
    public void addPath(File f) throws Exception {
        URI u = f.toURI();
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> urlClass = URLClassLoader.class;
        Method method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        method.invoke(urlClassLoader, new Object[]{u.toURL()});
    }
}