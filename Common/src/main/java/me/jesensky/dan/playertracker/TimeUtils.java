package me.jesensky.dan.playertracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class TimeUtils {
    public static String getTime() {
        return getTime("dd MM yyyy");
    }

    public static String getTime(String timeFormat) {
        return new SimpleDateFormat(timeFormat).format(Calendar.getInstance().getTime());
    }
}
