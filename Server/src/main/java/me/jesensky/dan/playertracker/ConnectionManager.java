package me.jesensky.dan.playertracker;

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

public class ConnectionManager extends Thread{
    private HashMap<InetAddress, Connection> connections;
    private ServerSocket socket;
    private boolean accepting;
    private List<ConnectionListener> listeners;

    public ConnectionManager(){
        this.connections = new HashMap<>();
        this.listeners = new ArrayList<ConnectionListener>();
    }

    public HashMap<InetAddress, Connection> getConnections(){
        return this.connections;
    }

    public Connection getConnection(InetSocketAddress addr){
        return this.connections.get(addr.getAddress());
    }

    public Connection getConnection(InetAddress addr){
        return this.connections.get(addr);
    }

    public void addConnectionListener(ConnectionListener listener){

    }

    @Override
    public void run(){
        while(this.accepting){
            try {
                Socket sock = this.socket.accept();
                this.connections.put(sock.getInetAddress(), new ClientConnection(sock));
            }catch(IOException | InvalidArgumentException e){
                //TODO log exception
            }
        }
        super.run();
    }

    @Override
    public String toString(){
        return "ConnectionManager@"+super.hashCode()+"[connections="+this.connections.size()+"]";
    }
}