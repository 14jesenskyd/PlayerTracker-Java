package me.jesensky.dan.playertracker.net.packets;

import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;

import java.util.Arrays;

public enum PacketType {
    LOGIN(new byte[]{0x1, 0x0, 0x1}),
    FETCH_DATA(new byte[]{0x1, 0x0, 0x2}),
    SEND_DATA(new byte[]{0x1, 0x0, 0x3}),
    ;

    PacketType(byte... data){
        this.header = data;
    }

    public byte[] getHeader(){
        return this.header;
    }

    public static PacketType getTypeFromHeader(byte... head) throws InvalidPacketException {
        for(PacketType t : PacketType.values()){
            if(Arrays.equals(t.getHeader(), head))
                return t;
        }
        throw new InvalidPacketException("Header did not match any packet types.");
    }

    private byte[] header;
}