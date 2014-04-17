package me.jesensky.dan.playertracker.net.packets;

import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;

public class DataResponsePacket extends Packet{
    public DataResponsePacket(byte... data) throws InvalidPacketException{
    super(PacketType.DATA_RESPONSE, data);
    }
}