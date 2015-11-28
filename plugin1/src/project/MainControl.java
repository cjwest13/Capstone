package project;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.ClosingSwipe;
import utilities.NextScreen;
import java.net.URL;
import java.util.ResourceBundle;

public class MainControl implements Initializable, NextScreen, ClosingSwipe {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private StackPane stackPane;

    private ImageView[] pictures;

    private static String pickPerson;

    private static ObservableList<String> people;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        people = FXCollections.observableArrayList();
        people.addAll("Scott Barlowe", "Mark Holliday", "William Kreahling", "Andrew Scott");
        pictures = new ImageView[3];
        addImages();
        startSlideShow();
        //closes application on swipeDown
        anchorPane.setOnSwipeDown(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                CloseTheApp();
            }
        });

        choiceBox.setItems(people);
        //choiceBox.setTooltip((new Tooltip("Select a CS Professor :D")));

        choiceBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> person, String before, String selected) {
                pickPerson = selected;
                System.out.println(pickPerson);
                goToNextScreen("/fxml/schedule.fxml");
                System.out.println("hiii");
            }
        });


    }

    private void addImages() {
        Image image1 = new Image(getClass().getResource("/pictures/pic1.jpg").toExternalForm(), 400, 400, true, true);
        Image image2 = new Image(getClass().getResource("/pictures/pic2.jpg").toExternalForm(), 400, 400, true, true);
        Image image3 = new Image(getClass().getResource("/pictures/pic3.jpg").toExternalForm(), 400, 400, true, true);
        pictures[0] = new ImageView(image1);
        pictures[1] = new ImageView(image2);
        pictures[2] = new ImageView(image3);
    }

    private void startSlideShow() {
        SequentialTransition slideshow = new SequentialTransition();

        for (ImageView slide : pictures) {
            SequentialTransition sequentialTransition = new SequentialTransition();

            FadeTransition fadeIn = getFadeTransition(slide, 0.0, 1.0, 2000);
            PauseTransition stayOn = new PauseTransition(Duration.millis(2000));
            FadeTransition fadeOut = getFadeTransition(slide, 1.0, 0.0, 2000);

            sequentialTransition.getChildren().addAll(fadeIn, stayOn, fadeOut);
            slide.setOpacity(0);
            stackPane.getChildren().add(slide);

            slideshow.getChildren().add(sequentialTransition);
        }
        slideshow.setCycleCount(Timeline.INDEFINITE);
        slideshow.play();
    }

    private FadeTransition getFadeTransition(ImageView imageView, double fromValue,
                                             double toValue, int durationInMilliseconds) {
        FadeTransition ft = new FadeTransition(Duration.millis(durationInMilliseconds), imageView);
        ft.setFromValue(fromValue);
        ft.setToValue(toValue);
        return ft;
    }
    @FXML
    public static ObservableList<String> getCurrentPeople() {
        return people;
    }

    @FXML
    public static String getPickPerson() {
        return pickPerson;
    }

}
