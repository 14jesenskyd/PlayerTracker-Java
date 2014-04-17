package me.jesensky.dan.playertracker.net;

import me.jesensky.dan.playertracker.exceptions.InvalidArgumentException;
import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.packets.Packet;
import me.jesensky.dan.playertracker.net.packets.PacketType;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection implements Closeable {
    protected Socket sock;

    public Connection(Socket sock) throws InvalidArgumentException {
        super();
        if (sock == null || sock.isClosed())
            throw new InvalidArgumentException("Provided socket may not be null or closed!");
        this.sock = sock;
    }

    public InputStream getInputStream() throws IOException {
        return this.sock.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.sock.getOutputStream();
    }

    public void sendUrgentData(int... i) throws IOException {
        for (int z : i)
            this.sock.sendUrgentData(z);
    }

    public Packet readData() throws IOException, InvalidPacketException {
        byte[] header = new byte[3];
        List<Byte> data = new ArrayList<Byte>();
        PacketType type;

        if (this.getInputStream().read(header) < 3)
            throw new InvalidPacketException("Packet header was too short.");
        type = PacketType.getTypeFromHeader(header);

        while (this.dataRemaining())
            data.add((byte) this.getInputStream().read());

        return new Packet(type, NetUtils.byteListToArray(data));
    }

    public boolean dataRemaining() throws IOException {
        return this.getInputStream().available() != 0;
    }

    public InetAddress getAddress() {
        return this.sock.getInetAddress();
    }

    public String getIP() {
        return this.sock.getInetAddress().getHostAddress();
    }

    public int getPort() {
        return this.sock.getPort();
    }

    public InetSocketAddress getSocketAddress() {
        return new InetSocketAddress(this.getAddress(), this.getPort());
    }

    public boolean isClosed() {
        return this.sock.isClosed();
    }

    @Override
    public void close() throws IOException {
        this.sock.close();
    }

    @Override
    public String toString() {
        return this.sock.toString();
    }
}
