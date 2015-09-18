package project;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void doDrugs(ActionEvent event) {
        System.out.println("Drugs are bad, mkay.");
    }

}
