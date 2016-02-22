package utilities.observer;

/**
 * Created by cjwest on 2/22/16.
 */
public interface Subject {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

}
