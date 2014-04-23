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

    /**
     * Instantiates a new {@code ConnectionManager}.<br />
     * <br />
     * {@code ConnectionManager} is a subclass of {@link java.lang.Thread},
     * and should be treated with care upon accessing any members.<br />
     * <br />
     * Given the purpose of the class, queries should be wary of
     * thread-safety with whatever actions they may perform.
     */
    public ConnectionManager(int port) throws IOException{
        super();
        this.socket = new ServerSocket();
        this.socket.bind(new InetSocketAddress("localhost", port));
        this.connections = new HashMap<>();
        this.listeners = new ArrayList<ConnectionListener>();
        this.accepting = true;
    }

    /**
     * Gets all of the currently live connections to the {@code Server}
     * singleton.<br />
     * <br />
     * Currently, the result is stored in a {@code HashMap}, ordered by
     * {@code InetAddress}es, which could lead to issues if more than one
     * person connects via the same ethernet uplink.<br />
     * <br />
     * As such, this implementation is volatile and subject to change at
     * any time, and is likely to do so in the future (likely to
     * {@code List}).
     *
     * @return A {@code HashMap} of all {@code Connection}s to the
     * {@code Server} singleton. Subject to change and should not
     * be relied upon.
     */
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
                Connection conn = new Connection(sock);
                this.connections.put(sock.getInetAddress(), conn);
                this.updateConnectionListeners(new ConnectionEvent(conn));
            } catch (IOException | InvalidArgumentException e) {
                Server.getLogger().error(e.getMessage());
            }
        }
        super.run();
    }

    /**
     * Calls {@link me.jesensky.dan.playertracker.listeners.ConnectionListener#onConnectEvent(me.jesensky.dan.playertracker.events.ConnectionEvent)}
     * for all {@code ConnectionListener}s registered to
     * this {@code ConnectionManager}. This method requires a
     * {@code ConnectionEvent} instance to pass to each
     * listener thereof.
     *
     * @param evt The {@code ConnectionEvent} to pass to each
     *            {@code ConnectionListener}.
     */
    private void updateConnectionListeners(ConnectionEvent evt) {
        for (ConnectionListener listener : this.listeners) {
            if (listener != null)
                listener.onConnectEvent(evt);
        }
    }

    public void closeConnections(){
        for(Connection connection : this.getConnections().values())
            if(connection != null && !connection.isClosed())
                try {
                    connection.close();
                    this.getConnections().remove(connection.getAddress());
                }catch(IOException e){
                    //ignore, likely already closed
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