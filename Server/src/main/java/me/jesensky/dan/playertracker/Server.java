package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.forms.ServerGUI;
import me.jesensky.dan.playertracker.net.Connection;
import me.jesensky.dan.playertracker.util.DatabaseManager;
import me.jesensky.dan.playertracker.util.Logger;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Server {
    private static Server singletonInstance;
    private ConnectionManager connectionManager;
    private DatabaseManager dbMan;
    private Logger log;
    private DataManager dataMan;
    private Configuration config;

    static {
        singletonInstance = null;
    }

    private Server() {
        super();
        try {
            this.log = new Logger("log.log");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            this.loadConfiguration("config");
            this.connectionManager.start();
            this.dbMan.connect();
            this.dataMan = new DataManager();
            this.dataMan.start();

            ResultSet r;
            PreparedStatement s = this.dbMan.prepareStatement("SELECT * FROM information_schema.tables WHERE table_schema = 'playertracker' AND table_name = 'players' LIMIT 1;");
            s.execute();
            r = s.getResultSet();
            if (!r.first()) {
                //TODO create table
            }
            s.close();
            r.close();

            s = this.dbMan.prepareStatement("SELECT * FROM information_schema.tables WHERE table_schema = 'playertracker' AND table_name = 'users' LIMIT 1;");
            s.execute();
            r = s.getResultSet();
            if (!r.first()) {
                //TODO create table
            }
            s.close();
            r.close();
        } catch (IOException | SQLException e) {
            this.log.error(e.getMessage());
        }
    }

    private Logger _getLogger(){
        return this.log;
    }

    public static Logger getLogger(){
        return Server.getSingleton()._getLogger();
    }

    public static Server getSingleton() {
        if (Server.singletonInstance == null)
            Server.singletonInstance = new Server();
        return Server.singletonInstance;
    }

    public ConnectionManager getConnectionManager() {
        return this.connectionManager;
    }

    public DatabaseManager getDbManager(){
        return this.dbMan;
    }

    public static void main(String[] args) {
        Server singleton = new Server();
        ServerGUI gui = new ServerGUI();
        singleton.getConnectionManager().registerConnectionListener((evt) -> gui.setConnections(singleton.getConnectionManager().getConnections().size()));
    }

    public HashMap<InetAddress, Connection> getConnections() {
        return this.connectionManager.getConnections();
    }

    private void loadConfiguration(String filename) throws IOException, SQLException {
        if(new File(filename).exists())
            this.config = Configuration.load(filename);
        else
            this.config = new Configuration();
        this.connectionManager = new ConnectionManager(this.config.<String>getValue("hostname", "::1"), this.config.<Integer>getValue("port", 1534));
        this.dbMan = new DatabaseManager(this.config.<String>getValue("db-hostname", "localhost"), this.config.<Integer>getValue("db-port", 3306), this.config.<String>getValue("db-user", "root"), this.config.<String>getValue("db-password", "root"), this.config.<String>getValue("db-database", "playertracker"));
    }

    @Override
    public String toString() {
        return "Server@" + super.hashCode() + "[" + this.getConnectionManager().toString() + "]";
    }
}