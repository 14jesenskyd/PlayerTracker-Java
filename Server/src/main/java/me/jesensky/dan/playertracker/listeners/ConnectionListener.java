package me.jesensky.dan.playertracker.listeners;

import me.jesensky.dan.playertracker.events.ConnectionEvent;

public interface ConnectionListener{
    public void onConnectEvent(ConnectionEvent evt);
}