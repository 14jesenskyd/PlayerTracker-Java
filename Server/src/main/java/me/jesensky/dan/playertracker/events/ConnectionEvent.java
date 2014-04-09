package me.jesensky.dan.playertracker.events;

import me.jesensky.dan.playertracker.net.Connection;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 14jesenskyd on 4/9/2014.
 */
public class ConnectionEvent {
    private Connection connection;
    private Calendar calendar;

    public ConnectionEvent(Connection c, Calendar cal) {
        this.connection = c;
        this.calendar = cal;
    }

    public ConnectionEvent(Connection c) {
        this(c, Calendar.getInstance());
    }
}