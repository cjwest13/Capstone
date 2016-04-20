package API;

import controller.GestureControl;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.omg.CORBA.BooleanHolder;

import java.util.Observable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Class methods for certain gesture controls.
 * Is the Subject for the Observer pattern of Single/Double Click events.
 * @author  Clifton West
 * @version March 20, 2016
 */
public class Gestures extends Observable implements GestureControl {

    /** First Coordinate in the X Direction */
    private double firstX;

    /** First Coordinate in the Y Direction */
    private double firstY;

    /** Second Coordinate in the X Direction */
    private double secX;

    /** Second Coordinate in the Y Direction */
    private double secY;

    /** The time when the User Pressed Down on the Screen */
    private long time1;

    /** The contains value of single/double click event */
    private int click;

    /** Flag to detect A Drag Event */
    private boolean dragFlag;

    /** Contains value if press and hold was detected */
    private BooleanHolder press;

    /** Schedule Commands to run after a given delay */
    private ScheduledThreadPoolExecutor executor;

    /** A delayed action caused by ScheduleThreadPoolExecutor */
    private ScheduledFuture<?> scheduledFuture;

    /**
     * Constructor for the Gestures class.
     */
    public Gestures() {
        executor = new ScheduledThreadPoolExecutor(1);
        executor.setRemoveOnCancelPolicy(true);
        press = new BooleanHolder(false);
        dragFlag = false;
        secX = 0;
        secY = 0;
        click = 0;
    }

    /**
     * Handler for the press and hold event. Duration is set to 1 Second.
     * @param node      Node that the user wants the press and hold event to apply too.
     * @param handler   MouseEvent EventHandler that details that desire event when event occurs.
     */
    @Override
    public void pressHoldHandler(Node node, EventHandler<MouseEvent> handler) {
        //Wrapper class for the event fired.
        class Wrapper<T> {T content ; }
        Wrapper<MouseEvent> eventWrapper = new Wrapper<>();

        PauseTransition holdTimer = new PauseTransition(Duration.seconds(1));
        holdTimer.setOnFinished(event -> {
            press.value = true;
            handler.handle(eventWrapper.content);
        });

        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            eventWrapper.content = event;
            holdTimer.playFromStart();
        });

        node.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            press.value = false;
            holdTimer.stop();
        });
        node.addEventHandler(MouseEvent.DRAG_DETECTED, event -> {
            press.value = false;
            holdTimer.stop();
        });
    }

    /**
     * Detects vertical swiping.
     * Must be implemented in {@link MouseEvent#MOUSE_CLICKED} event.
     * @param e MouseEvent MouseEvent triggered.
     */
    @Override
    public void clicks(MouseEvent e) {
        if (e.getButton().equals(MouseButton.PRIMARY)) {
            time1 = System.currentTimeMillis();
            if (e.getClickCount() == 2) {
                scheduledFuture.cancel(false);
                doubleClick();
            } else if (e.getClickCount() == 1) {
                scheduledFuture = executor.schedule(() -> singleClick(), 1000, TimeUnit.MILLISECONDS);
            }
        }
    }

    /**
     * Private method to detect single click, notify observers when single click event occurs.
     */
    private void singleClick() {
        if (!press.value && !dragFlag ) {
            synchronized (this) {
                click = 1;
            }
            setChanged();
            notifyObservers();
            click = 0;
        } else {
            dragFlag = false;
            press.value = false;
        }
        //return 1;
    }

    /**
     * Method use by observers to get single or double click.
     * @return Integer value that represents single of double click event. The integer values are as follows:<br>
     * 1: Single Click.<br>
     * 2: Double Click.
     */
    public synchronized int getClick() {
        return click;
    }

    /**
     * Private method to detect double click, notify observers when double click event occurs.
     */
    private void doubleClick() {
        synchronized (this) {
            click = 2;
        }
        setChanged();
        notifyObservers();
        click = 0;
    }

    /**
     * Detecting swiping from the diagonal from the top towards to the bottom.
     * Must be implemented in {@link MouseEvent#MOUSE_RELEASED} event.
     * @param X coordinate in the x direction
     * @param Y coordinate in the y direction
     * @return  Integer value that represents diagonal swipe event. The integer values are as follows:<br>
     * 1 = Left to Right Diagonal Swipe<br>
     * 2 = Right to Left Diagonal Swipe<br>
     * 3 = Close Event
     */
    @Override
    public int diagonalSwipe(double X, double Y) {
        secX = X;
        secY = Y;
        int value = 0;
        if (firstX == secX && firstY == secY) {
            value = 0;
        } else {
            dragFlag = true;
            double buffer = 50.1;
            //Detecting CLOSE
            if ((firstX + buffer) < secX && (firstY + buffer) < secY) {
                time1 = System.currentTimeMillis();
                value = 1;
            }
            if ((firstX - buffer) > secX && (firstY + buffer) < secY) {
                long time2 = System.currentTimeMillis();
                long diff = time2 - time1;
                if ((diff/1000) < 1) {
                    value = 3;
                } else {
                    time1 = 0;
                    value = 2;
                }
            }
        }
        return value;
    }

    /**
     * Detects vertical swiping.
     * Must be implemented in {@link MouseEvent#MOUSE_RELEASED} event.
     * @param X coordinate in the x direction
     * @param Y coordinate in the y direction
     * @return Integer value that represents direction of swiping. The integer values are as follows:<br>
     * 1 = Up to Down Swipe.<br>
     * 2 = Down to Up Swipe.
     */
    @Override
    public int verticalSwipe(double X, double Y) {
        secX = X;
        secY = Y;
        int value = 0;
        if (firstX == secX && firstY == secY) {
            value = 0;
        } else {
            dragFlag = true;
            double vertBuff = 50.0;
            if (firstX >= secX) {
                if (firstX - vertBuff <= secX) {
                    if (secY > firstY) {
                        value = 1;
                    } else {
                        value = 2;
                    }
                }
            }
            if (firstX <= secX) {
                if (firstX + vertBuff >= secX) {
                    if (secY < firstY) {
                        value = 2;
                    } else {
                        value = 1;
                    }
                }
            }
        }
        return value;
    }

    /**
     * Gets the first two coordinates of when user first clicks the screen to help detect drag events.
     * Must be implemented in the {@link MouseEvent#MOUSE_PRESSED} event and have to be implemented for {@link #verticalSwipe},
     * {@link #horizontalSwipe}, and {@link #diagonalSwipe} methods to work properly.
     * @param X coordinate in the x direction
     * @param Y coordinate in the y direction
     */
    @Override
    public void mouseEntered(double X, double Y) {
        firstX = X;
        firstY = Y;
    }

    /**
     * Detects horizontal swiping.
     * Must be implemented in {@link MouseEvent#MOUSE_RELEASED} event.
     * @param X coordinate in the x direction
     * @param Y coordinate in the y direction
     * @return Integer value that represents direction of swiping. The integer values are as follows:<br>
     * 1 = Left To Right Swipe.<br>
     * 2 = Right To Left Swipe.
     */
    @Override
    public int horizontalSwipe(double X, double Y) {
        secX = X;
        secY = Y;
        int value = 0;
        double buffer = 50.0;
        if (firstX == secX && firstY == secY) {
            value = 0;
        } else {
            dragFlag = true;
            if (firstY >= secY) {
                if ((firstY - buffer) <= secY) {
                    if (secX > firstX) {
                        value = 1;
                    } else {
                        value = 2;
                    }
                }
            }
            if (firstY <= secY) {
                if ((firstY + buffer) >= secY) {
                    if (secX < firstX) {
                        value = 2;
                    } else {
                        value = 1;
                    }
                }
            }
        }
        return value;
    }
}
