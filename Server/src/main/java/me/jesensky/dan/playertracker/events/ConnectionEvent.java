package me.jesensky.dan.playertracker.events;

import me.jesensky.dan.playertracker.TimeUtils;
import me.jesensky.dan.playertracker.net.Connection;

public class ConnectionEvent {
    private final Connection connection;
    private final String time;

    public ConnectionEvent(Connection c, String timeFormat) {
        this.connection = c;
        this.time = TimeUtils.getTime(timeFormat);
    }

    public ConnectionEvent(Connection c) {
        this(c, "dd MM yyyy");
    }

    public String getTimestamp() {
        return this.time;
    }

    public Connection getConnection() {
        return this.connection;
    }
}