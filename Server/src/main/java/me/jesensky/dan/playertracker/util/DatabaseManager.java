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
    private String table;
    private String db;
    private int port;
    public static final String PLAYER_DB_DEFAULT = "SELECT * FROM information_schema.tables WHERE table_schema = '?' AND table_name = 'players' LIMIT 1;";
    public static final String USER_DB_DEFAULT = "SELECT * FROM information_schema.tables WHERE table_schema = '?' AND table_name = 'users' LIMIT 1;";

    public DatabaseManager(String host, int port, String user, String password, String db, String table) throws SQLException {
        super();
        this.host = host;
        this.port = port;
        this.username = user;
        this.password = password;
        this.table = table;
        this.db = db;
    }

    public void connect(String defaults, String... args) throws SQLException{
        this.connect();
        PreparedStatement statement = this.connection.prepareStatement(defaults);
        for(int x = 0; x < args.length; x++)
            try {
                statement.setInt(x, Integer.parseInt(args[x]));
            }catch(NumberFormatException e){
                statement.setString(x, args[x]);
            }
        statement.execute();
        statement.close();
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