package API;

import javafx.animation.PauseTransition;

/**
 * API for the accessing the gesture controls.
 * @author  Clifton West
 * @version February 1, 2016
 */
public interface GestureControl {

    /**
     * If a user presses down on the screen
     * @param X coordinate in the x direction
     * @param Y coordinate in the y direction
     */
    void singleClick(double X, double Y);

    PauseTransition pressHold();

    int doubleClick(double X, double Y);


    int diagonalSwipe(double X, double Y);

    int horizontalSwipe(double X, double Y);

    int verticalSwipe(double X, double Y);

    void mouseEntered(double X, double Y);

    /**
     * If a user releases their finger off the screen
     * @param x coordinate in the x direction
     * @param y coordinate in the y direction
     */
    default void tapUp(int x, int y) {

    }

    /**
     * If a user slides their finger across the screen.
     * @param x     first coordinate in the x direction
     * @param x2    last coordinate in the x direction
     * @param y     first coordinate in the y direction
     * @param y2    second coordinate in the y direction
     */
    default void slide(int x, int x2, int y, int y2) {

    }

    /**
     * When a user taps the screen, drags their finger across the screen in whatever motion that they wish.
     * @param x array of coordinates in the x direction
     * @param y array of coordinates in the y direction
     */
    default void tapDragStopped(int[] x, int[] y) {

    }

    /**
     * Seeing if their where multiply taps on the Screen within a certain amount of time.
     */
    default void mutlitaps() {

    }
}
