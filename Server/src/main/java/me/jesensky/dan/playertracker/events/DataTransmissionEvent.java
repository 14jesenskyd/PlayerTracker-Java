package me.jesensky.dan.playertracker.events;

import me.jesensky.dan.playertracker.net.Connection;

public class DataTransmissionEvent {
    private final byte[] data;
    private final Connection connection;

    public DataTransmissionEvent(Connection connection, byte... data){
        this.data = data;
        this.connection = connection;
    }

    public Connection getConnection(){
        return this.connection;
    }

    public byte[] getData(){
        return this.data;
    }
}