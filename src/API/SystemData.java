package API;

import controller.SystemDataInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Clifton West
 * @version April 17, 2016.
 */
public class SystemData implements SystemDataInterface {

    /**
     * Gets the date and time (24 hour) in the format of "DayOfTheWeek Month Day
     * Hour:Minute:Second TimeZone Year."
     * @param time      The current time in a Date object.
     * @return String   The time and date returned.
     */
    @Override
    public String getDateAndTime(Date time) {
        return time.toString();
    }

    /**
     * Gets the date and time in the format of "DayOfTheWeek Month Day
     * Hour:Minute:Second TimeZone Year."
     * @param   time    The current time in a Date object.
     * @return  String  The date returned.
     */
    @Override
    public String getAppDate(Date time) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(calendar.getTime());
    }

    /**
     * Gets the time (24 hour) in the format of Hour:Minute:Sec TimeZone"
     * @param   time        The current time in a Date object.
     * @param   seconds     If seconds should be showed.
     * @param   timeZone    If the time zone should be showed.
     * @return  String      The time returned.
     */
    @Override
    public String get24HourTime(Date time, Boolean seconds, Boolean timeZone) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf;
        if (seconds) {
            if (timeZone) {
                sdf = new SimpleDateFormat("HH:mm:ss z");
            } else {
                sdf = new SimpleDateFormat("HH:mm:ss");
            }
        } else {
            if (timeZone) {
                sdf = new SimpleDateFormat("HH:mm z");
            } else {
                sdf = new SimpleDateFormat("HH:mm");
            }
        }
        return sdf.format(calendar.getTime());
    }

    /**
     * Gets the time (12 hour) in the format of Hour:Minute:Sec Am/Pm TimeZone"
     * @param   time        The current time in a Date object.
     * @param   seconds     If seconds should be showed.
     * @param   timeZone    If the time zone should be showed.
     * @return  String      The time returned.
     */
    @Override
    public String get12HourTime(Date time, Boolean seconds, Boolean timeZone) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf;
        if (seconds) {
            if (timeZone) {
                sdf = new SimpleDateFormat("hh:mm:ss a z");
            } else {
                sdf = new SimpleDateFormat("hh:mm:ss a");
            }
        } else {
            if (timeZone) {
                sdf = new SimpleDateFormat("hh:mm a z");
            } else {
                sdf = new SimpleDateFormat("hh:mm a");
            }
        }
        return sdf.format(calendar.getTime());
    }
}
