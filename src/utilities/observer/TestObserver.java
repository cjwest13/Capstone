package utilities.observer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cjwest on 2/22/16.
 */
public class TestObserver {

    public static void main(String[] args) {
        Internet internet = new Internet();
        Newspaper newspaper = new Newspaper();

        Time time = new Time();
        Timer timer = new Timer();

        time.addObserver(internet);
        time.addObserver(newspaper);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                time.setTime(date.toString());
            }
        }, 1000, 1000);
    }
}
