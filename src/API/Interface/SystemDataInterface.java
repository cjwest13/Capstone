package API.Interface;

import java.util.Date;

/**
 * API for providing system data.
 * @author  Clifton West
 * @version February 2, 2016
 */
public interface SystemDataInterface {

    /**
     * Gets the date and time (24 hour) in the format of "DayOfTheWeek Month Day
     * Hour:Minute:Second TimeZone Year."
     * @param time      The current time in a Date object.
     * @return String   The time and date returned.
     */
    String getDateAndTime(Date time);

    /**
     * Gets the date and time in the format of "DayOfTheWeek Month Day
     * Hour:Minute:Second TimeZone Year."
     * @param   time    The current time in a Date object.
     * @return  String  The date returned.
     */
    String getAppDate(Date time);

    /**
     * Gets the time (24 hour) in the format of Hour:Minute:Sec TimeZone"
     * @param   time        The current time in a Date object.
     * @param   seconds     If seconds should be showed.
     * @param   timeZone    If the time zone should be showed.
     * @return  String      The time returned.
     */
    String get24HourTime(Date time, Boolean seconds, Boolean timeZone);

    /**
     * Gets the time (12 hour) in the format of Hour:Minute:Sec Am/Pm TimeZone"
     * @param   time        The current time in a Date object.
     * @param   seconds     If seconds should be showed.
     * @param   timeZone    If the time zone should be showed.
     * @return  String      The time returned.
     */
    String get12HourTime(Date time, Boolean seconds, Boolean timeZone);
}
