package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import utilities.NextScreen;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Makes the main and first screen showed.
 * @author  Clifton West
 * @version November 3, 2015
 */
public class ScheduleController implements Observer, Initializable, NextScreen {

    /** WebView in the associated fxml */
    @FXML
    private WebView webView;

    /** Time Label in the associated fxml */
    @FXML
    private Label timeLbl;

    /** WebEngine in the associated fxml */
    private WebEngine webEngine;

    /** Person selected */
    private String person;

    /** People in the List */
    private ObservableList<String> people;

    /** Url to the Appropriate Website */
    private String url;

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
        people= MainController.getCurrentPeople();
        person = MainController.getPickPerson();
        url = pickedSchedule(person);
        webEngine = webView.getEngine();
        webEngine.load(url);
        webEngine.setUserStyleSheetLocation(getClass().getResource("/style/schedule.css").toString());
    }

    /**
     * Returns the Url of the Of the Website.
     * @param person String representing the person.
     * @return String representing the Website to go too.
     */
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
        Main.removeObserver(this);
        goToNextScreen("/fxml/main.fxml");
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
