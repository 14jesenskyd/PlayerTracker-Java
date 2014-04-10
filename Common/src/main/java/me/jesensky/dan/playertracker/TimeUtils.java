package me.jesensky.dan.playertracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class TimeUtils {

    /**
     * Overload of {@link #getTime(String)}.<br />
     * <br />
     * Gets the date at the instant of invocation, formatted as {@code dd MM yyyy}, i.e. {@code 10 April 2014}.
     *
     * @return The date, formatted as {@code dd MM yyyy}.
     * @see #getTime(String)
     */
    public static String getTime() {
        return getTime("dd MM yyyy");
    }

    /**
     * Gets the time at the instant of invocation. Includes date.
     *
     * @param timeFormat The format for the time to be
     *                   in, i.e. {@code "dd MM yyyy hh:mm:ss"}
     * @return The time string at the time of invocation,
     * formatted per the {@code timeFormat}.
     */
    public static String getTime(String timeFormat) {
        return new SimpleDateFormat(timeFormat).format(Calendar.getInstance().getTime());
    }
}
