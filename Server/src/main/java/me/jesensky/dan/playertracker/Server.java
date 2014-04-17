package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.forms.ServerGUI;
import me.jesensky.dan.playertracker.net.Connection;
import me.jesensky.dan.playertracker.util.DatabaseManager;

import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.beans.Statement;
import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Server {
    private static Server singletonInstance;
    private ConnectionManager connectionManager;
    private DatabaseManager dbMan;

    static {
        singletonInstance = null;
    }

    private Server() {
        super();
        this.connectionManager = new ConnectionManager();
        this.connectionManager.start();
        this.loadConfiguration();
        try {
            //TODO replace hard-coded test data with actual configuration

            this.dbMan = new DatabaseManager("::1", 1533, "root", "root", "playertracker_test");
            this.dbMan.connect();

            ResultSet r;
            PreparedStatement s = this.dbMan.prepareStatement("SELECT * FROM information_schema.tables WHERE table_schema = 'playertracker' AND table_name = 'players' LIMIT 1;");
            s.execute();
            s.close();
            r = s.getResultSet();
            if(!r.first()){
                //TODO create table
            }
            r.close();

            s = this.dbMan.prepareStatement("SELECT * FROM information_schema.tables WHERE table_schema = 'playertracker' AND table_name = 'users' LIMIT 1;");
            s.execute();
            s.close();
            r = s.getResultSet();
            if(!r.first()){
                //TODO create table
            }
            r.close();
        }catch(SQLException e){
            //TODO log exception
        }
    }

    public static Server getSingleton() {
        if (singletonInstance == null)
            singletonInstance = new Server();
        return singletonInstance;
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

    private void loadConfiguration() {

    }

    @Override
    public String toString() {
        return "Server@" + super.hashCode() + "[" + this.getConnectionManager().toString() + "]";
    }
}