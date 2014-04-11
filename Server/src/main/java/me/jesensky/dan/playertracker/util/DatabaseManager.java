package me.jesensky.dan.playertracker.util;

import com.mysql.jdbc.MySQLConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

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
    private String db;
    private int port;

    public DatabaseManager(String host, int port, String user, String password, String table, String db, String defaults) throws SQLException {
        this.host = host;
        this.port = port;
        this.username = user;
        this.password = password;
        this.table = table;
        this.db = db;
        this.connect(defaults);
    }

    private void connect(String defaults){
        MysqlDataSource d = new MysqlDataSource();
        d.setUser(this.username);
        d.setPassword(this.password);
        d.setServerName(this.host);
        d.setPort(this.port);
        d.setDatabaseName(this.db);
    }

    private void connect(){

    }
}