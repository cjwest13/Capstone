package API.Interface;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Interface that contains empty methods for certain gesture controls.
 * <br> This interface doesn't not implement observer pattern. Only in {@link API.Classes.Gestures} class.
 * @author  Clifton West
 * @version February 1, 2016
 */
public interface GestureControl {

    /**
     * Handler for the press and hold event. Duration is set to 1 Second.
     * @param node      Node that the user wants the press and hold event to apply too.
     * @param handler   MouseEvent EventHandler that details that desire event when event occurs.
     */
    void pressHoldHandler(Node node, EventHandler<MouseEvent> handler);

    /**
     * Detects vertical swiping.
     * Must be implemented in {@link MouseEvent#MOUSE_CLICKED} event.
     * @param e MouseEvent triggered.
     */
    void clicks(MouseEvent e);

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
    int diagonalSwipe(double X, double Y);

    /**
     * Detects vertical swiping.
     * Must be implemented in {@link MouseEvent#MOUSE_RELEASED} event.
     * @param X coordinate in the x direction
     * @param Y coordinate in the y direction
     * @return Integer value that represents direction of swiping. The integer values are as follows:<br>
     * 1 = Up to Down Swipe.<br>
     * 2 = Down to Up Swipe.
     */
    int verticalSwipe(double X, double Y);

    /**
     * Gets the first two coordinates of when user first clicks the screen to help detect drag events.
     * Must be implemented in the {@link MouseEvent#MOUSE_PRESSED} event and have to be implemented for {@link #verticalSwipe},
     * {@link #horizontalSwipe}, and {@link #diagonalSwipe} methods to work properly.
     * @param X coordinate in the x direction
     * @param Y coordinate in the y direction
     */
    void mouseEntered(double X, double Y);

    /**
     * Detects horizontal swiping.
     * Must be implemented in {@link MouseEvent#MOUSE_RELEASED} event.
     * @param X coordinate in the x direction
     * @param Y coordinate in the y direction
     * @return Integer value that represents direction of swiping. The integer values are as follows:<br>
     * 1 = Left To Right Swipe.<br>
     * 2 = Right To Left Swipe.
     */
    int horizontalSwipe(double X, double Y);

}
