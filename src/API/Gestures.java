package API;

import controller.GestureControl;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Created by cjwest on 4/2/16.
 */
public class Gestures implements GestureControl {

    private double firstX;
    private double firstY;
    private double secX;
    private double secY;
    private double firstClickX;
    private double firstClickY;
    private double secClickX;
    private double secClickY;
    private long time1;
    private long clickTime;
    private int clicks = 0;
    private int clicknum = 0;

    /**
     * If a user presses down on the screen
     *
     * @param X coordinate in the x direction
     * @param Y coordinate in the y direction
     */
    @Override
    public void singleClick(double X, double Y) {


    }

    @Override
    public PauseTransition pressHold() {
        PauseTransition holdTimer = new PauseTransition(Duration.seconds(1));
        return holdTimer;
    }

    //Mouse Click Event
    @Override
    public int doubleClick(double X, double Y) {
        double bufferX = 30.0;
        double bufferY = 30.0;
        if (clicks == 0) {
            firstClickX = X;
            firstClickY = Y;
            clicks = clicks + 1;
            clickTime = System.currentTimeMillis();
            clicknum = 1;
        } else if (clicks == 1) {
            long time2 = System.currentTimeMillis();
            long diff = time2 - clickTime;
            if ((diff/1000) < 1) {
                //)
                secClickX = X;
                secClickY = Y;
                if (firstClickX == secClickX && secClickY == firstClickY) {
                    clicknum = 2;
                } else if (Math.abs(firstClickX - secClickX) <= bufferX &&
                        Math.abs(firstClickY - secClickY) <= bufferY) {
                    clicknum = 2;
                }
            } else {
                clicks = 1;
                clickTime = time2;
                firstClickX = X;
                firstClickY = Y;
                clicknum = 1;
            }
        }
        return clicknum;
    }

    //mouse_released & close
    @Override
    public int diagonalSwipe(double X, double Y) {
        secX = X;
        secY = Y;
        int value = 0;
        if ((firstClickX - secClickX) <= 30.0 && (firstClickY - secClickY) <= 30.0) {
            value = 0;
        } else if (firstX == secX && firstY == secY) {
            value = 0;
        } else {
            double bufferX = 31.0;
            double bufferY = 31.0;
            //Detecting CLOSE
            if ((firstX + bufferX) < secX && (firstY + bufferY) < secY) {
                //System.out.println("Left to Right Diagonal Swipe");
                time1 = System.currentTimeMillis();
                value = 1;
            }
            if ((firstX - bufferX) > secX && (firstY + bufferY) < secY) {
                //System.out.println("Right to Left Diagonal Swipe");
                long time2 = System.currentTimeMillis();
                long diff = time2 - time1;
                if ((diff/1000) < 1) {
                    value = 3;
                    //System.out.println("AYYYEE CLOSE");
                } else {
                    time1 = 0;
                    value = 2;
                }
            }
        }
        return value;
    }
    //mouse_released & closev
    @Override
    public int verticalSwipe(double X, double Y) {
        secX = X;
        secY = Y;
        int value = 0;
        if ((firstClickX - secClickX) <= 30.0 && (firstClickY - secClickY) <= 30.0) {
            value = 0;
            //   event.consume();
        } else if (firstX == secX && firstY == secY) {
            value = 0;
            // event.consume();
        } else {
            double vertbuff=40.0;
            //Detecting Vertical Swiping
            if (firstX >= secX) {
                if (firstX - vertbuff <= secX) {
                    if (secY > firstY) {
                        //System.out.println("Up to Down Swipe");
                        value = 1;
                    } else {
                        value = 2;
                        //System.out.println("Down to Up Swipe");
                    }
                }
               // event.consume();
            }
            if (firstX <= secX) {
                if (firstX + vertbuff >= secX) {
                    if (secY < firstY) {
                        value = 2;
                        //System.out.println("Down to Up Swipe");
                    } else {
                        value = 1;
                        //System.out.println("Up to Down Swipe");
                    }
                }
            }
        }
        return value;
    }

    //Mouse Pressed
    @Override
    public void mouseEntered(double X, double Y) {
        firstX = X;
        firstY = Y;
    }

    //Mouse_released
    @Override
    public int horizontalSwipe(double X, double Y) {
        secX = X;
        secY = Y;
        int value = 0;
        double buffer = 31.0;
       // if ((firstClickX - secClickX) <= 30.0 && (firstClickY - secClickY) <= 30.0) {
        //    value = 0;
            //   event.consume();
        //} else
            if (firstX == secX && firstY == secY) {
            value = 0;
            // event.consume();
        } else {
            if (firstY >= secY) {
                if (firstY - buffer <= secY) {
                    if (secX > firstX) {
                        value = 1;
                        //System.out.println("Left To Right Swipe");
                    } else {
                        value = 2;
                        //System.out.println("Right To Left Swipe");
                    }
                }
                //event.consume();
            }

            if (firstY <= secY) {
                if (firstY + buffer >= secY) {
                    if (secX < firstX) {
                        value = 2;
                        //System.out.println("Right To Left Swipe");
                    } else {
                        value = 1;
                        //System.out.println("Left To Right Swipe");
                    }
                }
            }
        }

        return value;
    }
}
