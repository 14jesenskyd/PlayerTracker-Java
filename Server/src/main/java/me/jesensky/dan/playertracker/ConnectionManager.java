package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.events.ConnectionEvent;
import me.jesensky.dan.playertracker.exceptions.InvalidArgumentException;
import me.jesensky.dan.playertracker.listeners.ConnectionListener;
import me.jesensky.dan.playertracker.net.Connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConnectionManager extends Thread {
    private HashMap<InetAddress, Connection> connections;
    private ServerSocket socket;
    private boolean accepting;
    private List<ConnectionListener> listeners;

    public ConnectionManager() {
        super();
        this.connections = new HashMap<>();
        this.listeners = new ArrayList<ConnectionListener>();
    }

    public HashMap<InetAddress, Connection> getConnections() {
        return this.connections;
    }

    public Connection getConnection(InetSocketAddress addr) {
        return this.connections.get(addr.getAddress());
    }

    public Connection getConnection(InetAddress addr) {
        return this.connections.get(addr);
    }

    /**
     * Registers an observer to be invoked upon the server's
     * {@code ConnectionManager} receiving a new connection.
     * This will not fire if for some internal reason
     * the connection refuses abruptly.<br />
     * <br />
     * Returns an id -- that of the listener which is being registered.
     * This id can be used to remove the listener from operation, if
     * so desired.
     *
     * @param listener The {@code ConnectionListener} implementation to
     *                 invoke upon event firing.
     * @return The ID of the listener.
     * @see #unregisterConnectionListener(int)
     */
    public int registerConnectionListener(ConnectionListener listener) {
        if (!this.listeners.contains(listener)) {
            this.listeners.add(listener);
        }
        return this.listeners.indexOf(listener);
    }

    /**
     * Unregisters an observer from the queue of invocation when the
     * server receives a connection. Removing a listener will not affect
     * the ids of other listeners in any way. Using an index of {@code -1}
     * or that which exceeds the upper boundary of the listener listing
     * will cause nothing to happen.
     *
     * @param id The id to remove.
     */
    public void unregisterConnectionListener(int id) {
        if (id < 0 || id >= this.listeners.size())
            return;
        this.listeners.set(id, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        while (this.accepting) {
            try {
                Socket sock = this.socket.accept();
                ClientConnection conn = new ClientConnection(sock);
                this.connections.put(sock.getInetAddress(), conn);
                this.updateConnectionListeners(new ConnectionEvent(conn));
            } catch (IOException | InvalidArgumentException e) {
                //TODO log exception
            }
        }
        super.run();
    }

    private void updateConnectionListeners(ConnectionEvent evt) {
        for (ConnectionListener listener : this.listeners) {
            if (listener != null)
                listener.onConnectEvent(evt);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ConnectionManager@" + super.hashCode() + "[connections=" + this.connections.size() + "]";
    }
}