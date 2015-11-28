package project;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.NextScreen;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the PluginInfo.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 3, 2015
 */
public class PluginInfoController implements Initializable, NextScreen {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private StackPane stackPane;

    private File pictures;

    private String jarFile;

    private ArrayList<ImageView> pics;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pics = new ArrayList();
        setFiles();
        setSlideShow();
        anchorPane.setOnSwipeDown(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                Platform.exit();
            }
        });
        stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    String command = "java -jar "+ jarFile;
                    Runtime.getRuntime().exec(command);
                } catch (Exception e) {
                    e.printStackTrace();
               }
            }
        });
         
    }
    /**
     *
     */
    private void setSlideShow() {
        addImages();
        SequentialTransition slideshow = new SequentialTransition();
        for (ImageView slide : pics) {
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
    private void addImages() {
        File[] files = pictures.listFiles();
        for (File file: files) {
            String[] string = file.getPath().split("/resources");
            ImageView view = new ImageView(string[1]);
            view.setFitHeight(430);
            view.setPreserveRatio(true);
            pics.add(view);
        }
    }
    private void setFiles() {
        File[] plugin = MainController.getChosenPlugin();
        String[] string = plugin[0].getName().split("\\.");
        if (string.length > 1) {
            jarFile = plugin[0].getPath();
            pictures = plugin[1];
        } else {
            jarFile = plugin[1].getPath();
            pictures = plugin[0];
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