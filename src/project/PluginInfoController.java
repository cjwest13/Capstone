package project;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utilities.NextScreen;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
    private HBox hbox;

    private static List<List<File>> pluginsMain;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pluginsMain = MainController.getPluginsMain();
        anchorPane.setOnSwipeDown(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                Platform.exit();
            }
        });
        hbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //String hi = pluginsMain.get(0).get(0).getAbsolutePath();
                //String[] no = hi.split("/resources");
                String yes = "../1/fxml/main.fxml";
                //String yes = "../1/sample/sample.fxml";
                //ClassLoader cldr = this.getClass().getClassLoader();
                //URL url = cldr.getResource("./home/cjwest/Documents/resources/1/fxml/main.fxml");
                //URL url = getClass().getResource("../1/fxml/main.fxml");
                //URL url = cldr.getResource("../resources/1/pictures/pic1.jpg");

                try {
                //FXMLLoader fxmlLoader = new FXMLLoader();
               // fxmlLoader.setLocation(this.getClass().getResource(yes));
                   // fxmlLoader.setClassLoader(this.getClass().getClassLoader());

                    //System.out.println(fxmlLoader.getLocation());
                    //fxmlLoader.setLocation(url);
                    //fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                   // System.out.println(fxmlLoader.getLocation());
                   // Parent root = fxmlLoader.load();
                   // Stage stage = MainScreen.getStage();
                   // Scene scene = new Scene(root);
                  //  stage.setScene(scene);
                //if (fxmlLoader.getController() == null) {
                  //  System.out.println("NOOOOBISTSSS");
                //}

               // Stage stage = MainScreen.getStage();
                //Scene scene = new Scene()
                //stage.setScene();
                //System.out.println(url.getPath());

                //System.out.println(fxmlLoader.getController().toString());
                goToNextScreen(yes);
                //URL location = getClass().getResource(no);

                    //Parent p = FXMLLoader.load(getClass().getResource(no));
                    //FXMLLoader fxmlLoader = new FXMLLoader(url);
                    //Parent p = fxmlLoader.load();
                   // Scene scene = new Scene(p);
                    //Stage stage = MainScreen.getStage();
                    //stage.setScene(scene);
                //fxmlLoader.setController();
                //System.out.println(no);
                //Parent root = null;
                ///**
                //try {
                    //goToNextScreen(no);
                //Parent root = FXMLLoader.load(getClass().getResource(no), null, new JavaFXBuilderFactory());
                    //String e = getClass().getResource(no).toExternalForm();
                    //System.out.println(e);


                //InputStream in = new URL(e).openStream();
                    //FXMLLoader loader = new FXMLLoader();
                    //Parent p = loader.load(in);
                    //Scene scene = new Scene(p);
                    //Stage stage = MainScreen.getStage();
                   // stage.setScene(scene);

                    //System.out.println(in);
                    //System.out.println(p);
                    //con = loader.getController();
               // root = fxmlLoader.load();

                    //String ma = "/home/cjwest/Documents/resources/1/fxml";
                //goToNextScreen(no);


                   //System.out.println(pluginMain.getPath());
                    //System.out.println(pluginMain.getCanonicalPath());
                   //goToNextScreen(pluginMain.getCanonicalPath());
                    //goToNextScreen(pluginMain.getPath());
                } catch (Exception e) {
                    System.out.println("whattttt");
               }
                //Scene scene = new Scene(root, 800, 600);
               // Stage stage = new Stage();
               // stage.setScene(scene);
               // stage.show();
                //*/
            }
        });
    }

    /**
     * Function assigned to a fxml button that goes to the Main.fxml screen.
     */
    @FXML
    public void goToMain() {
        goToNextScreen("/fxml/Main.fxml");
    }

    /**
     * Function assigned to a fxml grid that loads the plugin's main screen.
     * @param fxml path to an fxml file.
     */
    public void goToPlugin(String fxml) {
       //NextScreen.;
    }
}