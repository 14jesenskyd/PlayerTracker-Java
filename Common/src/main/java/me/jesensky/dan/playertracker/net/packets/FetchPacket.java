package me.jesensky.dan.playertracker.net.packets;

import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.NetUtils;
import me.jesensky.dan.playertracker.net.packets.PacketType;

/**
 * {@inheritDoc}
 *
 * {@code FetchPacket} specifies a user to grab data for and then
 * return it to the client which sent it, in form of a
 * {@code DataResponsePacket}.
 */
public class FetchPacket extends Packet{
    public FetchPacket(byte... b) throws InvalidPacketException{
        super(PacketType.FETCH_DATA, b);
    }

    public String getName(){
        return NetUtils.bytesToString(super.getDataSection(0));
    }
}