package me.jesensky.dan.playertracker.observers;

import me.jesensky.dan.playertracker.ClientConnection;
import me.jesensky.dan.playertracker.events.DataTransmissionEvent;
import me.jesensky.dan.playertracker.listeners.DataTransmissionListener;

public class DataTransmissionObserver implements DataTransmissionListener{
    @Override
    public void dataReceived(DataTransmissionEvent evt) {
        if(!(evt.getConnection() instanceof ClientConnection))
            return;

    }

    @Override
    public void dataSent(DataTransmissionEvent evt) {
    }
}