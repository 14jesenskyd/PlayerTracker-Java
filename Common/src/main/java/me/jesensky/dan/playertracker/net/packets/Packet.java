package me.jesensky.dan.playertracker.net.packets;

import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.Connection;
import me.jesensky.dan.playertracker.net.NetUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents data, cached in memory, to be sent
 * somewhere over the Internet. This class is to be the super or base
 * class for all packets.<br />
 * <br />
 * Implementations will find it useful to call
 * {@link #getDataSection(int)}, as it will return
 * the data within a specified <i>section</i> of the packet.
 * A <i>section</i> is defined as a series of {@code byte}s
 * between two {@code 0x0} bytes, or the beginning, and/or
 * end, if either endpoint does not exist.<br />
 * <br />
 * Data may be flushed from this buffer to a
 * {@link java.net.Socket} or
 * {@link me.jesensky.dan.playertracker.net.Connection}
 * by invoking
 * {@link #sendData(java.net.Socket)} or
 * {@link #sendData(me.jesensky.dan.playertracker.net.Connection)}.
 */
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

    public void sendData(Connection conn) throws IOException {
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

    /**
     * Gets a section of the data cached within
     * this {@code Packet}, based on the specified
     * {@code section}. A {@code section} is defined
     * explicitly as the data between two separator
     * bytes ({@code 0x0}), or the beginning and/or the
     * end of the data stored if there is no such endpoint.<br />
     * <br />
     * Sections begin counting at 0, just as arrays do.
     *
     * @param section The section of data to get, bound by
     *                {@code 0x0}, the beginning, end, or any
     *                combination thereof, depending on what data
     *                is stored.
     *
     * @return The bytes between the endpoints of the specified
     * {@code section}.
     */
    public byte[] getDataSection(int section){
        List<Byte> r = new ArrayList<Byte>();
        int iteration = 0, index = 0;
        boolean t = true;

        while(t){
            if(this.data[index] == 0)
                iteration++;
            if(iteration == section){
                if(section != 0)
                    index++;
                while(t && index < this.data.length){
                    if(this.data[index] == (byte)0x0){
                        t = false;
                    }else{
                        r.add(this.data[index++]);
                    }
                    if(this.data.length == index)
                        t = false;
                }
            }
            index++;
        }
        return NetUtils.byteListToArray(r);
    }
}