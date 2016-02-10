package API;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * API for providing system data.
 * @author  Clifton West
 * @version February 2, 2016
 */
public interface SystemData {

    /**
     * Gets the date and time (24 hour) in the format of "DayOfTheWeek Month Day
     * Hour:Minute:Second TimeZone Year."
     * @return String The time and date returned.
     */
    default String getDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        return time.toString();
    }

    /**
     * Gets the date and time in the format of "DayOfTheWeek Month Day
     * Hour:Minute:Second TimeZone Year."
     * @return String The date returned.
     */
    default String getAppDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(calendar.getTime());
    }

    /**
     * Gets the time (24 hour) in the format of Hour:Minute:Sec TimeZone"
     * @param   seconds     If seconds should be showed.
     * @param   timeZone    If the time zone should be showed.
     * @return  String      The time returned.
     */
    default String get24HourTime(Boolean seconds, Boolean timeZone) {
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
     * @param seconds   If seconds should be showed.
     * @param timeZone  If the time zone should be showed.
     * @return String   The time returned.
     */
    default String get12HourTime(Boolean seconds, Boolean timeZone) {
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
