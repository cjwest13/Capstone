package utilities.observer;

/**
 * Created by cjwest on 2/22/16.
 */
public class Newspaper implements Observer {

    @Override
    public void update(String time) {
        System.out.println("Newspaper time is " + time);
    }
}
