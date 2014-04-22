package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.Connection;
import me.jesensky.dan.playertracker.net.packets.DataResponsePacket;
import me.jesensky.dan.playertracker.net.packets.LoginResponsePacket;
import me.jesensky.dan.playertracker.net.packets.Packet;
import me.jesensky.dan.playertracker.net.packets.PacketType;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestManager extends Thread {
    private Connection c;
    private Queue<Packet> responses;

    public RequestManager(Connection c) {
        super("Data Request Manager");
        this.c = c;
        this.responses = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
        try {
            if (this.c.dataRemaining()) {
                Packet p = this.c.readData();

                switch (p.getType()) {
                    case LOGIN_RESPONSE:
                        this.responses.add((LoginResponsePacket)p);
                        break;
                    case DATA_RESPONSE:
                        this.responses.add((DataResponsePacket)p);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException | InvalidPacketException e) {
            Client.getLogger().error(e.getMessage());
        }
        super.run();
    }

    public boolean hasResponse(){
        return !this.responses.isEmpty();
    }

    public Packet getResponse(){
        return this.responses.poll();
    }
}