package me.jesensky.dan.playertracker.net.packets;

import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.Connection;

import java.io.IOException;
import java.net.Socket;

public class Packet {
    private PacketType type;
    private byte[] data;

    public Packet(PacketType type, byte... data) throws InvalidPacketException {
        super();
        this.type = type;
        this.data = data;
    }

    public PacketType getType() {
        return this.type;
    }

    public void sendData(Socket sock) throws IOException {
        sock.getOutputStream().write(this.getAmmendedData());
    }

    public void sendData(Connection conn) throws IOException{
        conn.getOutputStream().write(this.getAmmendedData());
    }

    public byte[] getAmmendedData() {
        byte[] sigma = new byte[this.data.length + this.type.getHeader().length];
        System.arraycopy(this.type.getHeader(), 0, sigma, 0, this.type.getHeader().length);
        System.arraycopy(this.data, 0, sigma, this.type.getHeader().length, this.data.length);
        return sigma;
    }

    @Override
    public String toString() {
        String s = "Packet@" + super.hashCode() + "{";
        for (byte b : this.getAmmendedData())
            s += b + ", ";
        return s.substring(0, s.length() - 2) + "}";
    }
}