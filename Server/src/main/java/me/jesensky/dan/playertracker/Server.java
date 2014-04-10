package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.forms.ServerGUI;
import me.jesensky.dan.playertracker.net.Connection;

import java.net.InetAddress;
import java.util.HashMap;

public class Server {
    private static Server singletonInstance;
    private ConnectionManager connectionManager;

    static {
        singletonInstance = null;
    }

    private Server() {
        super();
        this.connectionManager = new ConnectionManager();
        this.connectionManager.start();
        this.loadConfiguration();
    }

    public static Server getSingleton() {
        if (singletonInstance == null)
            singletonInstance = new Server();
        return singletonInstance;
    }

    public ConnectionManager getConnectionManager() {
        return this.connectionManager;
    }

    public static void main(String[] args) {
        Server singleton = new Server();
        ServerGUI gui = new ServerGUI();
        singleton.getConnectionManager().registerConnectionListener((evt) -> gui.setConnections(singleton.getConnectionManager().getConnections().size()));
    }

    public HashMap<InetAddress, Connection> getConnections() {
        return this.connectionManager.getConnections();
    }

    private void loadConfiguration() {

    }

    @Override
    public String toString() {
        return "Server@" + super.hashCode() + "[" + this.getConnectionManager().toString() + "]";
    }
}