package API;

/**
 * API for the accessing the methods associated with components.
 * @author  Clifton West
 * @version February 1, 2016
 */
public interface ComponentControl {

    /**
     * Assessing the custom made Screens.
     * @param n number of which Screen to access.
     */
    default void guiScreen(int n) {

    }

    /**
     * Assessing the custom made GUI Components.
     * @param n number of which Component to access.
     */
    default void guiComponent(int n){

    }

    /**
     * Accessing the custom made keyboard.
     */
    default void getKeyboard() {

    }
}
