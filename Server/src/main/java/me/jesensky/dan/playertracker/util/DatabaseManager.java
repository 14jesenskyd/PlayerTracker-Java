package me.jesensky.dan.playertracker.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;
    private String host;
    private String username;
    private String password;
    private String table;
    private int port;

    public DatabaseManager(String host, int port, String user, String password, String table, String defaults) throws SQLException {
        this.host = host;
        this.port = port;
        this.username = user;
        this.password = password;
        this.table = table;
        this.connect(defaults);
    }

    private void connect(String defaults){
    }

    private void connect(){

    }
}