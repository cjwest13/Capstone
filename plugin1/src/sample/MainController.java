package sample;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private StackPane stackPane;

    private ImageView[] pictures;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pictures = new ImageView[3];
        addImages();
        start();

        choiceBox.setItems(getCurrentPeople());
        choiceBox.setTooltip((new Tooltip("Select a CS Professor :D")));

        choiceBox.valueProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> person, Person before, Person selected) {
                System.out.println(selected);
                goToNextScreen("/fxml/schedule.fxml");
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

    private void start() {

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

    private ObservableList<Person> getCurrentPeople() {
        ObservableList<Person> people = FXCollections.observableArrayList();

        people.addAll(new Person("Scott", "Barlowe"), new Person("Mark", "Holliday"),
        new Person("William", "Kreahling"), new Person("Andrew", "Scott"));

        return people;
    }
    private void createSlideShow() {

    }

    /**
     * Function assigned to a fxml button that goes to the Settings.fxml screen.
     */
    private void goToNextScreen(String fxml) {
        Parent loadScreen;
        try {
            loadScreen = FXMLLoader.load(getClass().getResource(fxml));
            FadeTransition ft = new FadeTransition(Duration.millis(3000), loadScreen);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Scene scene = new Scene(loadScreen);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioe) {
            System.err.println("File not found");
        }

    }

}
