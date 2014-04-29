package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.listeners.DataTransmissionListener;
import me.jesensky.dan.playertracker.net.Connection;
import me.jesensky.dan.playertracker.net.packets.DataResponsePacket;
import me.jesensky.dan.playertracker.net.packets.FetchPacket;
import me.jesensky.dan.playertracker.net.packets.LoginResponsePacket;
import me.jesensky.dan.playertracker.net.packets.Packet;

import java.io.IOException;
import java.util.List;

public class DataManager extends Thread {
    private List<DataTransmissionListener> listeners;

    public DataManager() {
        super("Data Response Manager");
    }

    public int registerDataTransmissionListener(DataTransmissionListener listener) {
        if (!this.listeners.contains(listener))
            this.listeners.add(listener);
        return this.listeners.indexOf(listener);
    }

    private String generateUUID() {
        //TODO generate
        return "";
    }

    private boolean attemptLogin(String user, String pass) {
        //TODO verify credentials
        Server.getSingleton().getDbManager();
        return false;
    }

    @Override
    public void run() {
        while (true)
            for (Connection c : Server.getSingleton().getConnectionManager().getConnections().values()) {
                if (c.isClosed())
                    Server.getSingleton().getConnectionManager().getConnections().remove(c.getAddress());
                else
                    try {
                        if (c.dataRemaining()) {
                            Packet p = c.readData();
                            Packet response = null;

                            switch (p.getType()) {
                                case LOGIN:
                                    response = new LoginResponsePacket(LoginResponsePacket.LoginResponse.SUCCESS);
                                    response.sendData(c);
                                    break;
                                case FETCH_DATA:
                                    FetchPacket packet = (FetchPacket) p;
                                    String name, notes, violations;
                                    UserViolationLevel vl;
                                    //TODO query db for information on player
                                    response = new DataResponsePacket();
                                    break;
                                default:
                                    continue;
                            }
                            response.sendData(c);
                        }
                    } catch (IOException | InvalidPacketException e) {
                        Server.getLogger().error(e.getMessage());
                    }
            }
    }
}