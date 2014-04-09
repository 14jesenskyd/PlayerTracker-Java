package me.jesensky.dan.playertracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 14jesenskyd on 4/9/2014.
 */
public final class TimeUtils {
    public static String getTime() {
        return getTime("dd MM yyyy");
    }

    public static String getTime(String timeFormat) {
        return new SimpleDateFormat(timeFormat).format(Calendar.getInstance().getTime());
    }
}
