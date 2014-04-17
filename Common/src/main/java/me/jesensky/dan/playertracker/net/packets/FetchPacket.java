package me.jesensky.dan.playertracker.net.packets;

import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.packets.PacketType;

public class FetchPacket extends Packet{
    public FetchPacket(byte... b) throws InvalidPacketException{
        super(PacketType.FETCH_DATA, b);
    }

    public String getName(){
        return "";
    }
}