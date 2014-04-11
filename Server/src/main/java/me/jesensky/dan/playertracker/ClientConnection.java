package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.exceptions.InvalidArgumentException;
import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.Connection;
import me.jesensky.dan.playertracker.net.NetUtils;
import me.jesensky.dan.playertracker.net.packets.Packet;
import me.jesensky.dan.playertracker.net.packets.PacketType;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientConnection extends Connection {
    public String uuid;

    public ClientConnection(Socket sock) throws InvalidArgumentException {
        super(sock);
    }

    @Override
    public Packet readData() throws IOException, InvalidPacketException{
        byte[] header = new byte[3];
        List<Byte> data = new ArrayList<Byte>();
        PacketType type;

        if(super.getInputStream().read(header) < 3)
            throw new InvalidPacketException("Packet header was too short.");
        type = PacketType.getTypeFromHeader(header);

        while(super.dataRemaining())
            data.add((byte)super.getInputStream().read());

        return new Packet(type, NetUtils.byteListToArray(data));
    }

    @Override
    public String toString(){
        return "ClientConnection@"+super.hashCode()+"["+this.uuid+"]";
    }
}
