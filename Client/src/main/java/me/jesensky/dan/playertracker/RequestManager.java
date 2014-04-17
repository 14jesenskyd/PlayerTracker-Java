package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.Connection;
import me.jesensky.dan.playertracker.net.packets.DataResponsePacket;
import me.jesensky.dan.playertracker.net.packets.Packet;

import java.io.IOException;

public class RequestManager extends Thread {
    private Connection c;

    public RequestManager(Connection c) {
        super("Data Request Manager");
        this.c = c;
    }

    @Override
    public void run() {
        try {
            if (this.c.dataRemaining()) {
                Packet p = this.c.readData();

                switch (p.getType()) {
                    case LOGIN_RESPONSE:

                        break;
                    case DATA_RESPONSE:
                        DataResponsePacket packet = (DataResponsePacket) p;
                        String name, notes, violations;
                        UserViolationLevel vl;
                        name = packet.getName();
                        notes = packet.getNotes();
                        violations = packet.getViolations();
                        vl = packet.getViolationLevel();
                        //TODO show a new UI detailing the player whose details were received
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException | InvalidPacketException e) {
            //TODO log exception
        }
        super.run();
    }
}