package me.jesensky.dan.playertracker.listeners;

import me.jesensky.dan.playertracker.events.ConnectionEvent;

public interface ConnectionListener {
    /**
     * This method will fire whenever a connection is
     * received by the server; however, only if it accepted
     * without exception. There is one folley, however: this
     * method will fire regardless of whether or not the connection
     * was logged in or not, i.e. if a user connects but uses the
     * wrong password, the event is still fired. If the user connects,
     * but the socket is for some reason invalid on the server side or
     * a read exception occurs <b>initially upon acceptance</b>, then
     * the event will not fire.<br />
     * <br />
     * Implementations should be sure to call {@link me.jesensky.dan.playertracker.ConnectionManager#registerConnectionListener(ConnectionListener)}
     *
     * @param evt The {@code ConnectionEvent} which shall contain information about the event fired.
     */
    public void onConnectEvent(ConnectionEvent evt);
}