package utilities.observer;

import java.util.ArrayList;

/**
 * Created by cjwest on 2/22/16.
 */
public class Time implements Subject {
    private ArrayList<Observer> observers = new ArrayList<>();

    private String time = "";

    public void setTime(String time) {
        this.time = time;
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer ob: observers) {
            System.out.println("Here's your updated time! ");
            ob.update(time);
        }
    }
}
