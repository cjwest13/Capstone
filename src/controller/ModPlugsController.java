package controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Duration;
import utilities.NextScreen;
import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * Controller class for ModPlugs.fxml file.
 * @author  Clifton West
 * @version October 3, 2015
 */
public class ModPlugsController implements Observer, Initializable, NextScreen {

    /** */
    @FXML
    private Label timeLbl;
    /** */

    @FXML
    private GridPane gridPane;

    /** An arraylist of the labels in the grid */
    private ArrayList<Label> labels;

    /** An arraylist of the labels in the grid */
    private List<List<File>> packages;

    /** An arraylist of the labels in the grid */
    private static ArrayList<ImageView> icons;

    /** An arraylist of the labels in the grid */
    private static List<List<String>> appData;

    /** An arraylist of the labels in the grid */
    private ContextMenu contextMenu;

    /** Dialog popup box */
    private TextInputDialog dialog;

    /** Dialog popup box */
    Dialog<String> confirmDialog;

    /** Close Button for the Dialog box */
    private ButtonType close;

    /** */
    private ButtonType buttonTypeOk;

    /** */
    private String result;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainScreen.addNewObserver(this);
        labels = new ArrayList();
        dialog = new TextInputDialog();
        confirmDialog = new Dialog<>();
        close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        packages = MainController.getPlugins();
        //System.out.println("SIIII");
        icons = MainController.getIcons();
        appData = MainController.getAppNames();
        buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        System.out.println(appData.size());
        //System.out.println("OMGGG");
        contextMenu = new ContextMenu();
        addPlugins();
        //createContextMenu();
        //setEvents();
    }

    /**
     * Creating the Context Menu
     * @param label
     */
    private void createContextMenu(Label label) {
        MenuItem item1 = new MenuItem("Change Name");
        item1.setOnAction(event -> {
            makeDialog("Change Name", "Enter a new name for the plugin.");
            Optional<String>entered = dialog.showAndWait();
            TextField textField = dialog.getEditor();
            dialog.setResultConverter(new Callback<ButtonType, String>() {
                @Override
                public String call(ButtonType b) {
                    if (b == buttonTypeOk) {
                            return textField.getText();
                    }
                    return null;
                }
            });
            //System.out.println(entered.get());
            result = entered.get();
            label.setText(result);
            MainController.changeAppData(gridPane.getRowIndex(label), true, result);
            //System.out.println(dialog.getContentText());
        });
        //MenuItem item2 = new MenuItem("Change Icon");
        //item2.setOnAction(event -> {

      //  });
        MenuItem item3 = new MenuItem("Change Description");
        item3.setOnAction(event -> {
            makeDialog("Change Description", "Enter a new description for the plugin.");
            Optional<String>entered = dialog.showAndWait();
            TextField textField = dialog.getEditor();
            dialog.setResultConverter(new Callback<ButtonType, String>() {
                @Override
                public String call(ButtonType b) {
                    if (b == buttonTypeOk) {
                        return textField.getText();
                    }
                    return null;
                }
            });
            //System.out.println(entered.get());
            result = entered.get();
            //label.setText(result);
            MainController.changeAppData(gridPane.getRowIndex(label), false, result);
        });
        MenuItem item4 = new MenuItem("Delete App");
        item4.setOnAction(event -> {
            int index = gridPane.getRowIndex(label);
            List<File> plugin = packages.get(index);
            //System.out.println(plugin.toString());
            Iterator iterator = plugin.iterator();
            while (iterator.hasNext()) {
                File file = (File) iterator.next();
                deleteFiles(file);
            }
            int number = index + 1;
            //File numFile = new File("/home/touchmeister/resources/"+number);
            File numFile = new File("/home/cjwest/resources/"+number);
            Boolean check = numFile.delete();
            if (check) {
                makeconfirmDialog("Deletion", "Deletion was successful");
                //gridPane.getChildren().remove(label);
                gridPane.getChildren().remove(label);
                gridPane.getChildren().remove(icons.get(index));
            } else {
                makeconfirmDialog("Deletion", "Deletion was unsuccessful");
            }
            //Iterator iterator = packages.get(i).iterator();

        });
        //contextMenu.getItems().addAll(item1, new SeparatorMenuItem(), item2, new SeparatorMenuItem(),
          //                              item3, new SeparatorMenuItem(), item4);
        contextMenu.getItems().addAll(item1, new SeparatorMenuItem(), item3, new SeparatorMenuItem(), item4);
    }

    /**
     * Deleting the Plugin
     * @param file
     */
    private void deleteFiles(File file) {
        if (file.isDirectory()) {
            for (File f: file.listFiles()) {
                deleteFiles(f);
            }
        }
        file.delete();
    }

    /**
     * Adding plugins to the grid
     */
    private void addPlugins() {
        if (packages != null) {
            if (packages.size() != 0 && appData.size() != 0) {
                //File icon;
                ImageView view;
                //Image image = null;
                for (int i = 0; i < packages.size(); i++) {
                    //System.out.println(icons.size());
                    view = icons.get(i);
                    labels.add(new Label(appData.get(i).get(0)));
                    // System.out.println("BBBB");
                    gridPane.add(labels.get(i), 1, i);
                    //System.out.println("CCCC");
                    gridPane.add(view, 0, i);
                    //System.out.println("DDDD");
                }
            }
            setEvents();
        }
    }

    /**
     * Setting events for the labels.
     */
    private void setEvents() {
        for (Label label: labels) {
            createContextMenu(label);
            label.setContextMenu(contextMenu);
            PauseTransition holdTimer = new PauseTransition(Duration.seconds(1));
            holdTimer.setOnFinished(event -> contextMenu.show(label, label.getLayoutX(), label.getLayoutY() - 50));
            label.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                holdTimer.playFromStart();
            });
            label.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
                holdTimer.stop();
            });
            label.addEventHandler(MouseEvent.DRAG_DETECTED, event -> {
                holdTimer.stop();
            });
        }
    }
    /**
     * Changes the following plugin.
     */
    @FXML
    public void editPlugin() {

    }

    /**
     * Deletes the following plugin.
     */
    @FXML
    public void deletePlugin() {

    }

    /**
     * Private method to create the dialog popup box.
     * @param title     Title of the dialog box.
     * @param message   Message inside of the dialog box.
     */
    private void makeDialog(String title, String message) {
        //dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.setTitle(title);
        dialog.setHeight(200);
        dialog.setHeaderText(message);
        //dialog.setContentText(message);

    }

    /**
     * Private method to create the confirmation dialog popup box.
     * @param title     Title of the dialog box.
     * @param message   Message inside of the dialog box.
     */
    private void makeconfirmDialog(String title, String message) {
        //dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        confirmDialog.getDialogPane().getButtonTypes().add(close);
        confirmDialog.setTitle(title);
        confirmDialog.setHeight(200);
        confirmDialog.setContentText(message);
        confirmDialog.showAndWait();
        //dialog.setContentText(message);

    }

    /**
     * Function assigned to a fxml button that goes to the Settings.fxml screen.
     */
    @FXML
    public void goToSettings() {
        NextScreen.super.goToNextScreen("/fxml/Settings.fxml");
    }

    /**
     * Update the time
     * @param date
     */
    @Override
    public void update(Date date) {
        timeLbl.setText(date.toString());
    }
}