package controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
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
public class ModPlugsController extends ClickObserver implements Observer, Initializable, NextScreen {

    /** Label representing the time in the screen */
    @FXML
    private Label timeLbl;

    /** GridPane representing the gridPane in the screen*/
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

    /** Dialog popup box */
    Dialog<String> confirmDialog;

    /** Close Button for the Dialog box */
    private ButtonType close;

    /** ButtonType */
    private ButtonType buttonTypeOk;

    /** Contains the new String */
    private String result;

    /** The path to the jarFile */
    private Gestures gestures = new Gestures();

    /** EventHandler */
    private EventHandler<MouseEvent> handler1;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainScreen.addObserver(this);
        observe();
        labels = new ArrayList();
        confirmDialog = new Dialog<>();
        close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        packages = MainController.getPlugins();
        icons = MainController.getIcons();
        appData = MainController.getAppNames();
        buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        System.out.println(appData.size());
        EventHandler<MouseEvent> handler2 = event1 -> System.out.println("PRESS THAT TAP");
        gestures.pressHoldHandler(timeLbl, handler2);
        handler1 = event -> gestures.mouseEntered(event.getX(), event.getY());
        MainScreen.getStage().addEventHandler(MouseEvent.MOUSE_PRESSED, handler1);
        //Testing stuff heere (Proof by doing gestures) Make sure to delete later
        MainScreen.getStage().addEventHandler(MouseEvent.MOUSE_RELEASED, event1 -> {
            int horzValue = gestures.horizontalSwipe(event1.getX(), event1.getY());
            int diaValue = gestures.diagonalSwipe(event1.getX(), event1.getY());
            int vertValue = gestures.verticalSwipe(event1.getX(), event1.getY());
            if (horzValue == 1) {
                System.out.println("Left To Right Swipe");
            } else if (horzValue == 2) {
                System.out.println("Right To Left Swipe");
            }
            if (diaValue == 1) {
                System.out.println("Left To Right Diagonal Swipe");
            } else if (diaValue == 2) {
                System.out.println("Right To Left Diagonal Swipe");
            } else if (diaValue == 3) {
                System.out.println("Close");
                Platform.exit();
            }
            if (vertValue == 1) {
                System.out.println("Up to Down Swipe");
            } else if (vertValue == 2) {
                System.out.println("Down to Up Swipe");
            }
                });
        MainScreen.getStage().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gestures.clicks(event);
        });
        addPlugins();
    }
    public void observe() {
        gestures.addObserver(this);
    }
    /**
     * Creating the Context Menu
     * @param label
     */
    private ContextMenu createContextMenu(Label label) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Change Name");
        item1.setOnAction(event -> {
            TextInputDialog dialog = makeDialog("Change Name", "Enter a new name for the plugin.");
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
            if (entered.get() != null) {
                result = entered.get();
                label.setText(result);
                MainController.changeAppData(gridPane.getRowIndex(label), true, result);
            }
        });
        //MenuItem item2 = new MenuItem("Change Icon");
        //item2.setOnAction(event -> {

      //  });
        MenuItem item3 = new MenuItem("Change Description");
        item3.setOnAction(event -> {
            TextInputDialog dialog = makeDialog("Change Description", "Enter a new description for the plugin.", appData.get(gridPane.getRowIndex(label)).get(1));
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
            if (entered.get() != null) {
                result = entered.get();
                MainController.changeAppData(gridPane.getRowIndex(label), false, result);
            }
        });
        MenuItem item4 = new MenuItem("Delete App");
        item4.setOnAction(event -> {
            int index = gridPane.getRowIndex(label);
            List<File> plugin = packages.get(index);
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
        });
        contextMenu.getItems().addAll(item1, new SeparatorMenuItem(), item3, new SeparatorMenuItem(), item4);
        return contextMenu;
    }

    /**
     * Deleting the Plugin and all of its files.
     * @param file The plugin's directory
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
     * Adding plugins to the grid.
     */
    private void addPlugins() {
        if (packages != null) {
            if (packages.size() != 0 && appData.size() != 0) {
                ImageView view;
                for (int i = 0; i < packages.size(); i++) {
                    view = icons.get(i);
                    labels.add(new Label(appData.get(i).get(0)));
                    gridPane.add(labels.get(i), 1, i);
                    gridPane.add(view, 0, i);
                }
            }
            setEvents();
        }
    }

    /**
     * Setting events for the labels.
     */
    private void setEvents() {
        /**
        PauseTransition[] pauses = new PauseTransition[labels.size()];
        for (PauseTransition pause: pauses) {
            pause = new PauseTransition(Duration.seconds(1));
        }
         */
        //System.out.println(pauses.length);
        for (Label label: labels) {
            ContextMenu contextMenu = createContextMenu(label);

            label.setContextMenu(contextMenu);
            //label.addEventHandler(MouseEvent.MOUSE_PRESSED, event ->
                //contextMenu.show(label, label.getLayoutX(), label.getLayoutY() - 50)
           // );
            //PauseTransition holdTimer = pauses[gridPane.getRowIndex(label)];
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
     * @return TextInputDialog popup box.
     */
    private TextInputDialog makeDialog(String title, String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeight(200);
        dialog.setHeaderText(message);
        return dialog;
    }

    /**
     * Private method to create the dialog popup box.
     * @param title     Title of the dialog box.
     * @param message   Message inside of the dialog box.
     * @param text      Text to go inside of the popup box.
     * @return TextInputDialog popup box.
     */
    private TextInputDialog makeDialog(String title, String message, String text) {
        TextInputDialog dialog = new TextInputDialog(text);
        dialog.setTitle(title);
        dialog.setHeight(200);
        dialog.setHeaderText(message);
        return dialog;
    }

    /**
     * Private method to create the confirmation dialog popup box.
     * @param title     Title of the dialog box.
     * @param message   Message inside of the dialog box.
     */
    private void makeconfirmDialog(String title, String message) {
        confirmDialog.getDialogPane().getButtonTypes().add(close);
        confirmDialog.setTitle(title);
        confirmDialog.setHeight(200);
        confirmDialog.setContentText(message);
        confirmDialog.showAndWait();
    }

    /**
     * Function assigned to a fxml button that goes to the Settings.fxml screen.
     */
    @FXML
    public void goToSettings() {
        MainScreen.removeObserver(this);
        NextScreen.super.goToNextScreen("/fxml/Settings.fxml");
    }

    /**
     * Update the time to the time label.
     * @param date Date object that is passed.
     */
    @Override
    public void update(Date date) {
        timeLbl.setText(date.toString());
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    //@Override
    public void update(Observable o, Object arg) {
        int value = ((Gestures) o).getClick();
        if (value == 1) {
            System.out.println("Single Click");
        } else if (value == 2) {
            System.out.println("Double Click");
        }
    }
}