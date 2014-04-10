package me.jesensky.dan.playertracker.listeners;

import me.jesensky.dan.playertracker.events.DataTransmissionEvent;

public interface DataTransmissionListener {
    public void dataSent(DataTransmissionEvent evt);

    public void dataReceived(DataTransmissionEvent evt);
}