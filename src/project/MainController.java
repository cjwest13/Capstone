package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button appsButton;

    @FXML
    private Button settingsButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void hi(ActionEvent e) {
        System.out.println("hi");
    }

    public void goToSettings(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Settings.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.setFullScreenExitHint("");
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.setFullScreen(true);

            stage.show();
        } catch (IOException ioe) {
            System.err.println("File not found");
        }
    }

}