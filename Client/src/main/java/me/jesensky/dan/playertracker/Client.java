package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.exceptions.InvalidArgumentException;
import me.jesensky.dan.playertracker.net.Connection;
import me.jesensky.dan.playertracker.util.Logger;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static Client client;
    private Logger logger;
    private Connection connection;
    private RequestManager requestMan;

    private Client() throws IOException{
        super();
        this.logger = new Logger("log.log");
    }

    static{
        client = null;
    }

    public static void main(String[] args) {
        Client c = getClient();
        ClientUI ui = new ClientUI();
    }

    public static Logger getLogger(){
        return getClient()._getLogger();
    }

    public void connect(String host, int port) throws InvalidArgumentException, IOException{
        this.connection = new Connection(new Socket(host, port));
        this.requestMan = new RequestManager(this.connection);
    }

    public static Client getClient(){
        if(Client.client == null)
            try {
                client = new Client();
            }catch(IOException e){
                JOptionPane.showMessageDialog(null, "Logging is disabled: "+e.getMessage());
            }
        return Client.client;
    }

    public RequestManager getRequestManager(){
        return this.requestMan;
    }

    public Connection getConnection(){
        return this.connection;
    }

    private Logger _getLogger(){
        return this.logger;
    }

    @Override
    public String toString(){
        return "Client[connected="+this.connection.isClosed()+"]";
    }
}