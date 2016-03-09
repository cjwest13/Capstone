package API;

import java.util.Date;

/**
 * An interface that Observers for the current time should implement.
 * @author  Clifton West
 * @version February 22, 2016
 */
public interface Observer {

    /**
     * This method is called whenever the observed object is changed.
     * @param time  The current time in a Date object.
     */
    void update(Date time);

}