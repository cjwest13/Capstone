package utilities;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import controller.MainScreen;
import org.omg.CORBA.DoubleHolder;
import org.omg.CORBA.IntHolder;
import org.omg.CORBA.LongHolder;

import javax.management.monitor.MonitorSettingException;
import java.io.IOException;

/**
 * Class that goes to the screen specified by the fxml file.
 * @author Clifton West
 * @version November 2, 2015
 */
public interface NextScreen {

    /**
     * A default method that goes to the screen according to the fxml file that is passed.
     * @param fxml path to an fxml file.
     */
    default void goToNextScreen(String fxml) {
        Parent loadScreen;
        Stage stage = MainScreen.getCurrentStage();

        /**
        Timeline timeline = new Timeline();
        EventHandler handler = event1 -> { timeline.stop(); timeline.play(); };
        KeyFrame key = new KeyFrame(Duration.seconds(20), event -> {
            stage.removeEventHandler(MouseEvent.ANY, handler);
            goToScreenSaver("/fxml/Main.fxml", fxml);
        });
         */
        //timeline.getKeyFrames().add(key);
        try {
            loadScreen = FXMLLoader.load(getClass().getResource(fxml));
            FadeTransition ft = new FadeTransition(Duration.millis(3000), loadScreen);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Scene scene = new Scene(loadScreen);
            //stage.addEventHandler(MouseEvent.ANY, handler);
            //addEvents(stage);
            //timeline.play();
            stage.setScene(scene);
            //Platform.setImplicitExit(false);
            stage.show();
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
            MainScreen.setFxml(fxml);
        } catch (IOException ioe) {
            System.err.println("File not found");
        }
    }

    default void addEvents(Stage stage) {
        //double firstX;
        //double firstY;
        //double secX;
        //double secY;
        final DoubleHolder firstX = new DoubleHolder();
        final DoubleHolder firstY = new DoubleHolder();
        final DoubleHolder secX = new DoubleHolder();
        final DoubleHolder secY = new DoubleHolder();
        final DoubleHolder firstclickX = new DoubleHolder();
        final DoubleHolder firstclickY = new DoubleHolder();
        final DoubleHolder secclickX = new DoubleHolder();
        final DoubleHolder secclickY = new DoubleHolder();
        final LongHolder time1 = new LongHolder();
        final LongHolder clickTime = new LongHolder();
        final IntHolder clicks = new IntHolder(0);

        clicks.value = 0;

        //stage.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
           // int value = mutliClicks()
            //if (event1.getClickCount() == 2) {
            //    System.out.println("MMSSSS");
            //}
            //firstX.value = event1.getX();
            //firstY.value = event1.getY();
            /**
            double bufferX = 30.0;
            double bufferY = 30.0;
            if (clicks.value == 0) {
                firstclickX.value = event1.getX();
                firstclickY.value = event1.getY();
                clicks.value = clicks.value + 1;
                clickTime.value = System.currentTimeMillis();
                event1.consume();
            } else if (clicks.value == 1) {
                //System.out.println("First Coordinates: " + firstclickX.value + " " + firstclickY.value);
                //System.out.println("Second Coordinates: " + secclickX.value + " " + secclickY.value);
           // }
            //if (clicks.value == 2) {
                long time2 = System.currentTimeMillis();
                long diff = time2 - clickTime.value;
                //System.out.println(clickTime.value);
                //System.out.println(time2);
                //System.out.println(diff);
                //System.out.println(diff/1000);
                if ((diff/1000) < 1) {
                    //)
                    secclickX.value = event1.getX();
                    secclickY.value = event1.getY();
                    if (firstclickX.value == secclickX.value
                            && secclickY.value == firstclickY.value) {
                        System.out.println("First Coordinates: " + firstclickX.value + " " + firstclickY.value);
                        System.out.println("Second Coordinates: " + secclickX.value + " " + secclickY.value);
                        System.out.println("DOUBLE CLICK");
                        event1.consume();
                    } else if (Math.abs(firstclickX.value - secclickX.value) <= bufferX &&
                            Math.abs(firstclickY.value - secclickY.value) <= bufferY) {
                        System.out.println("First Coordinates: " + firstclickX.value + " " + firstclickY.value);
                        System.out.println("Second Coordinates: " + secclickX.value + " " + secclickY.value);
                        System.out.println("DOUBLE CLICKAHHHH");
                        event1.consume();
                    }
                } else {
                    clicks.value = 1;
                    clickTime.value = time2;
                    firstclickX.value = event1.getX();
                    firstclickY.value = event1.getY();
                    event1.consume();
                }
            }
             */
       // });

       stage.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            firstX.value = event.getX();
            firstY.value = event.getY();
           System.out.println("First Coordinates: " + firstX.value + " " + firstY.value);
           event.consume();
        });


        //firstX = resultX.value;
        //System.out.println(resultX.value);
        //System.out.println(firstX);
        //firstY = resultY.value;
        stage.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            secX.value = event.getX();
            secY.value = event.getY();
            //System.out.println("First Coordinates: " + firstX.value + " " + firstY.value);
            System.out.println("Second Coordinates: " + secX.value + " " + secY.value);
            if ((firstclickX.value - secclickX.value) <= 30.0 &&
                    (firstclickY.value - secclickY.value) <= 30.0) {
                event.consume();
            } else if (firstX.value == secX.value && firstY.value == secY.value) {
                event.consume();
            } else {
                double bufferX = 31.0;
                double bufferY = 31.0;
                long time2 = 0;
                long diff = 0;
                //Detecting Diagonal Swipe
                if ((firstX.value+bufferX) < secX.value && (firstY.value+bufferY) < secY.value) {
                    System.out.println("Left to Right Diagonal Swipe");
                    time1.value = System.currentTimeMillis();
                    event.consume();
                }
                //Detecting Diagonal Swipe and Closing pattern
                if ((firstX.value - bufferX) > secX.value && (firstY.value+bufferY) < secY.value) {
                    System.out.println("Right to Left Diagonal Swipe");
                    time2 = System.currentTimeMillis();
                    diff = time2 - time1.value;
                    //System.out.println(time1.value);
                    //System.outprintln(time2);
                    //System.out.println(diff);
                    //System.out.println(diff/1000);
                    if ((diff/1000) < 1) {
                        System.out.println("AYYYEE CLOSE");
                        //event.consume();
                    } else {
                        time1.value = 0;
                    }
                    event.consume();
                }
                double vertbuff=40.0;
                //Detecting Vertica.l Swiping
                if (firstX.value >= secX.value) {
                    if (firstX.value - vertbuff <= secX.value) {
                        //if (Math.abs(firstX.value - secX.value) >= bufferX) {
                        if (secY.value > firstY.value) {
                            System.out.println("Up to Down Swipe");
                        } else {
                            System.out.println("Down to Up Swipe");
                        }
                    }
                    event.consume();
                }
                if (firstX.value <= secX.value) {
                    if (firstX.value + vertbuff >= secX.value) {
                        if (secY.value < firstY.value) {
                            System.out.println("Down to Up Swipe");
                        } else {
                            System.out.println("Up to Down Swipe");
                        }
                    }
                    event.consume();
                }

                //Detecting Horizontal Swiping
                if (firstY.value >= secY.value) {
                    if (firstY.value - bufferY <= secY.value) {
                        if (secX.value > firstX.value) {
                            System.out.println("Left To Right Swipe");
                        } else {
                            System.out.println("Right To Left Swipe");
                        }
                    }
                    event.consume();
                }
                if (firstY.value <= secY.value) {
                    if (firstY.value + bufferY >= secY.value) {
                        if (secX.value < firstX.value) {
                            System.out.println("Right To Left Swipe");
                        } else {
                            System.out.println("Left To Right Swipe");
                        }
                    }
                }
                event.consume();
            }
        });
        //secX = resultX.value;
        //secY = resultY.value;

        //System.out.println("First Coordinates: " + firstX + " " + firstY);
        //System.out.println("Second Coordinates: " + secX + " " + secY);
    }

    /**
     * A default method that goes to the screensaver screen via the fxml file that is passed.
     * @param fxml      path to an fxml file.
     #* @param curfxml   path to fxml file that left of on.
     */
    default void goToScreenSaver(String fxml) {
        Parent loadScreen;
        try {
            loadScreen = FXMLLoader.load(getClass().getResource(fxml));
            FadeTransition ft = new FadeTransition(Duration.millis(3000), loadScreen);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Scene scene = new Scene(loadScreen);
            Stage stage = MainScreen.getCurrentStage();
            stage.setScene(scene);
            //stage.requestFocus();
            stage.show();
            stage.setFullScreenExitHint("");
           stage.setFullScreen(true);

        } catch (IOException ioe) {
            System.err.println("File not found");
        }
    }
}
