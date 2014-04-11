package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.listeners.DataTransmissionListener;
import me.jesensky.dan.playertracker.net.Connection;
import me.jesensky.dan.playertracker.net.packets.LoginResponsePacket;
import me.jesensky.dan.playertracker.net.packets.Packet;
import me.jesensky.dan.playertracker.net.packets.PacketType;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class DataManager extends Thread{
    private List<DataTransmissionListener> listeners;

    public DataManager(){
        super("Data Response Manager");
    }

    public int registerDataTransmissionListener(DataTransmissionListener listener){
        if(!this.listeners.contains(listener))
            this.listeners.add(listener);
        return this.listeners.indexOf(listener);
    }

    private String generateUUID(){
        //TODO generate
        return "";
    }

    private boolean attemptLogin(String user, String pass){
        //TODO verify credentials
        //Server.getSingleton().getDbManager();
        return false;
    }

    @Override
    public void run(){
        for(Connection c : Server.getSingleton().getConnectionManager().getConnections().values()){
            try {
                if (c instanceof ClientConnection) {
                    if (c.dataRemaining()) {
                        Packet p = c.readData();
                        Packet response = null;

                        switch(p.getType()){
                            case LOGIN:

                                response = new LoginResponsePacket((byte)3);
                                break;
                            default:
                                continue;
                        }
                        response.sendData(c);
                    }
                }
            }catch(IOException | InvalidPacketException e){
                //TODO log exception
            }
        }
    }
}