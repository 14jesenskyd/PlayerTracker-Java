package me.jesensky.dan.playertracker.net.packets;

import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.NetUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * {@code LoginPacket} represents a packet to log into the server
 * for all other queries.
 */
public class LoginPacket extends Packet {
    public LoginPacket(String user, String pass) throws InvalidPacketException, UnsupportedEncodingException {
        super(PacketType.LOGIN, getBytesFromInformation(user, pass));
    }

    private static byte[] getBytesFromInformation(String user, String pass) throws UnsupportedEncodingException {
        List<Byte> b = new ArrayList<>();
        for(Byte z : user.getBytes("UTF-8"))
            b.add(z);
        b.add((byte)0x0);
        for(Byte z : NetUtils.getMD5Hash(pass.getBytes("UTF-8")))
            b.add(z);
        return NetUtils.byteListToArray(b);
    }
}