package API.Observers;

import java.util.Observer;

/**
 * Wrapper for the java.util.Observer class, used to detect the Clicks in the Gesture Class. <br>
 * Implement this class to detected single and doubles. <br>
 * After Implementation, you have to added your class to the {@link API.Classes.Gestures} observer pattern by
 * Creating a instance of gestures class, adding your class to the Gestures object's
 * observer list.
 * Implementing this class will force you utilize the update observer method. Which if
 * you cast it, in a {@link API.Classes.Gestures} object and utilizing the getClick
 * method in Gestures.
 * <br>
 * Example: <br>
 * <code>
 * Gestures gestures = new Gestures();
 * <br>gestures.addObserver(this);
 * <br>public void update(Observable o, Object arg) { <br>
 *      int value = ((Gestures) 0).getClick(); <br>
 * }
 * </code>
 * @author  Clifton West
 * @version April 15, 2016
 */
public abstract class ClickObserver implements Observer {


}
