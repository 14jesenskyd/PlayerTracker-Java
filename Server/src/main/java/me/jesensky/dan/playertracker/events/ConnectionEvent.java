package me.jesensky.dan.playertracker.events;

import me.jesensky.dan.playertracker.TimeUtils;
import me.jesensky.dan.playertracker.net.Connection;

import java.util.Calendar;

public class ConnectionEvent {
    private Connection connection;
    private String time;

    public ConnectionEvent(Connection c, String timeFormat) {
        this.connection = c;
        this.time = TimeUtils.getTime(timeFormat);
    }

    public ConnectionEvent(Connection c) {
        this(c, "dd MM yyyy");
    }

    public String getTimestamp(){
        return this.time;
    }

    public Connection getConnection(){
        return this.connection;
    }
}