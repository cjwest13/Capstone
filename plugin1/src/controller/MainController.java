package controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import utilities.NextScreen;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Main Controller for Main.fxml.
 * @author  Clifton West
 * @version November 3, 2015
 */
public class MainController implements Observer, Initializable, NextScreen{

    /** AnchorPane of the main.fxml */
    @FXML
    private AnchorPane anchorPane;

    /** ChoiceBox of the main.fxml */
    @FXML
    private ChoiceBox choiceBox;

    /** StackPane of the main.fxml */
    @FXML
    private StackPane stackPane;

    /** BorderPane of the main.fxml */
    @FXML
    private BorderPane borderPane;

    /** Name label of the main.fxml */
    @FXML
    private Label nameLbl;

    /** Time label of the main.fxml */
    @FXML
    private Label timeLbl;

    /** Pictures to be used in slideshow */
    private ImageView[] pictures;

    /** Person selected */
    private static String pickPerson;

    /** People in the List */
    private static ObservableList<String> people;

    /** SystemData object */
    SystemData time;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.addObserver(this);
        time = new SystemData();
        people = FXCollections.observableArrayList();
        people.addAll("Scott Barlowe", "Mark Holliday", "William Kreahling", "Andrew Scott");
        pictures = new ImageView[3];
        addImages();
        startSlideShow();
        //closes application on swipeDown
        /**
        anchorPane.setOnSwipeDown(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                Platform.exit();
            }
        });
        */
        borderPane.setOnSwipeDown(event -> Platform.exit());
        stackPane.setOnSwipeDown(event -> Platform.exit());
        choiceBox.setItems(people);
        //choiceBox.setTooltip((new Tooltip("Select a CS Professor :D")));
        choiceBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> person, String before, String selected) {
                pickPerson = selected;
                //System.out.println(pickPerson);
                goToNextScreen("/fxml/schedule.fxml");
            }
        });
    }

    /**
     * Add Images to the Slideshow
     */
    private void addImages() {
        Image image1 = new Image(getClass().getResource("/pictures/pic1.jpg").toExternalForm());
        Image image2 = new Image(getClass().getResource("/pictures/pic2.jpg").toExternalForm());
        Image image3 = new Image(getClass().getResource("/pictures/pic3.jpg").toExternalForm());
        pictures[0] = new ImageView(image1);
        pictures[1] = new ImageView(image2);
        pictures[2] = new ImageView(image3);
    }

    /**
     * Slideshow of pictures.
     */
    private void startSlideShow() {
        SequentialTransition slideshow = new SequentialTransition();

        for (ImageView slide : pictures) {
            slide.setFitHeight(350);
            slide.setPreserveRatio(true);
            SequentialTransition sequentialTransition = new SequentialTransition();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), slide);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            PauseTransition stayOn = new PauseTransition(Duration.millis(2000));
            FadeTransition fadeOut = new FadeTransition(Duration.millis(2000), slide);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            sequentialTransition.getChildren().addAll(fadeIn, stayOn, fadeOut);
            slide.setOpacity(0);
            stackPane.getChildren().add(slide);
            slideshow.getChildren().add(sequentialTransition);
        }
        slideshow.setCycleCount(Timeline.INDEFINITE);
        slideshow.play();
    }

    /**
     * Returns the current people in the list.
     * @return The current people in the list.
     */
    @FXML
    public static ObservableList<String> getCurrentPeople() {
        return people;
    }

    /**
     * Returns the people selected.
     * @return The person picked.
     */
    @FXML
    public static String getPickPerson() {
        return pickPerson;
    }

    /**
     * Sets label to the current time.
     * @param date  Date object that is passed.
     */
    @Override
    public void update(Date date) {
        timeLbl.setText(time.get12HourTime(date, false, true));
    }
}
