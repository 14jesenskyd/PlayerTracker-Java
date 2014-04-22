package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.net.Connection;
import me.jesensky.dan.playertracker.util.Logger;

public class Client {
    private static Client client;
    private Logger logger;
    private Connection connection;

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

    public static Client getClient(){
        if(Client.client == null)
            client = new Client();
        return Client.client;
    }

    private Logger _getLogger(){
        return this.logger;
    }

    @Override
    public String toString(){
        return "Client[connected="+this.connection.isClosed()+"]";
    }
}