package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import utilities.NextScreen;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by cjwest on 10/27/15.
 */
public class ScheduleController implements Initializable, NextScreen {

    @FXML
    private WebView webView;
    @FXML
    private Label timeLbl;
    private WebEngine webEngine;
    private String person;
    private ObservableList<String> people;
    private String url;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time();
        people= MainControl.getCurrentPeople();
        person = MainControl.getPickPerson();
        url = pickedSchedule(person);
        webEngine = webView.getEngine();
        webEngine.load(url);
    }

    private void time() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), event -> {
            Calendar calendar = Calendar.getInstance();
            Date time = calendar.getTime();
            timeLbl.setText(time.toString());
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private String pickedSchedule(String person) {
        String url = "";
        if (people.get(0).equals(person)) {
            url = "http://agora.cs.wcu.edu/~sbarlowe/currentSched.html";
        } else if (people.get(1).equals(person)) {
            url = "http://agora.cs.wcu.edu/~holliday/";
        } else if (people.get(2).equals(person)) {
            url = "http://agora.cs.wcu.edu/~wck/schedule.shtml";
        } else if (people.get(3).equals(person)) {
            url = "http://agora.cs.wcu.edu/~ascott/schedule.shtml";
        }
        return url;
    }

    /**
     * Function assigned to a fxml button that goes to the Settings.fxml screen.
     */
    @FXML
    public void goBack() {
        goToNextScreen("/fxml/main.fxml");
    }
}
