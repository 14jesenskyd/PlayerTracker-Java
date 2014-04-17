package me.jesensky.dan.playertracker.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;
    private String host;
    private String username;
    private String password;
    private String db;
    private int port;

    public DatabaseManager(String host, int port, String user, String password, String db) throws SQLException {
        super();
        this.host = host;
        this.port = port;
        this.username = user;
        this.password = password;
        this.db = db;
    }

    public void connect() throws SQLException{
        MysqlDataSource d = new MysqlDataSource();
        d.setUser(this.username);
        d.setPassword(this.password);
        d.setServerName(this.host);
        d.setPort(this.port);
        d.setDatabaseName(this.db);
        this.connection = d.getConnection();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException{
        return this.connection.prepareStatement(sql);
    }
}