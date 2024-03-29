package me.jesensky.dan.playertracker.net.packets;

import me.jesensky.dan.playertracker.exceptions.InvalidArgumentException;
import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.NetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 * <p>
 * {@code LoginResponsePacket} represents the response to
 * a login request -- which can be either success or failure.
 */
public class LoginResponsePacket extends Packet {

    public LoginResponsePacket(LoginResponse response) throws InvalidPacketException {
        this(response, "");
    }

    public LoginResponsePacket(Packet p) {
        super(p);
    }

    public LoginResponsePacket(LoginResponse response, String uuid) throws InvalidPacketException {
        this(getBytes(response, uuid));
    }

    public LoginResponsePacket(byte... bytes) throws InvalidPacketException {
        super(PacketType.LOGIN_RESPONSE, bytes);
    }

    private static byte[] getBytes(LoginResponse response, String uuid) {
        List<Byte> bytes = new ArrayList<Byte>();

        bytes.add(response.getResponse());
        bytes.add((byte) 0x0);
        for (byte b : NetUtils.stringToBytes(uuid))
            bytes.add(b);

        return NetUtils.byteListToArray(bytes);
    }

    public String getUUID() {
        return "";
    }

    public LoginResponse getResponse() throws InvalidArgumentException {
        return LoginResponse.getResponseFromByte(super.getAmmendedData()[3]);
    }

    public enum LoginResponse {
        SUCCESS((byte) 0x1),
        FAILURE((byte) 0x0);
        private byte indicator;

        LoginResponse(byte indicator) {
            this.indicator = indicator;
        }

        public static LoginResponse getResponseFromByte(byte b) throws InvalidArgumentException {
            for (LoginResponse r : LoginResponse.values())
                if (r.getResponse() == b)
                    return r;
            throw new InvalidArgumentException("Given byte does not correspond to any login responses.");
        }

        public byte getResponse() {
            return this.indicator;
        }

        @Override
        public String toString() {
            return "LoginResponse@" + super.hashCode() + "[" + super.toString() + "]";
        }
    }
}