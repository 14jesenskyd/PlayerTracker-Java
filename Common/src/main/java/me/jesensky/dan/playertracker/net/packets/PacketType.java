package me.jesensky.dan.playertracker.net.packets;

import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;

import java.util.Arrays;

public enum PacketType {
    LOGIN(new byte[]{0x1, 0x0, 0x1}),
    LOGIN_RESPONSE(new byte[]{0x1, 0x0, 0x2}),
    FETCH_DATA(new byte[]{0x1, 0x0, 0x3}),
    DATA_RESPONSE(new byte[]{0x1, 0x0, 0x4}),;
    private byte[] header;

    PacketType(byte... data) {
        this.header = data;
    }

    public static PacketType getTypeFromHeader(byte... head) throws InvalidPacketException {
        for (PacketType t : PacketType.values()) {
            if (Arrays.equals(t.getHeader(), head))
                return t;
        }
        throw new InvalidPacketException("Header did not match any packet types.");
    }

    public byte[] getHeader() {
        return this.header;
    }
}