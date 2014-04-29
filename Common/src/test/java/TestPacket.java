import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.packets.Packet;
import me.jesensky.dan.playertracker.net.packets.PacketType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.UnsupportedEncodingException;

@RunWith(JUnit4.class)
public class TestPacket {
    @Test
    public void packetHeader() throws InvalidPacketException {
        Packet packet = new Packet(PacketType.LOGIN, new byte[]{3, 4, 6, 3, 1, 6});
        Assert.assertEquals(packet.getType(), PacketType.LOGIN);
    }

    @Test
    public void _packetHeader() throws InvalidPacketException {
        Packet packet = new Packet(PacketType.LOGIN, new byte[]{3, 4, 6, 3, 1, 6});
        Assert.assertArrayEquals(packet.getType().getHeader(), new byte[]{1, 0, 1});
    }

    @Test
    public void ammendedData() throws InvalidPacketException {
        Packet packet = new Packet(PacketType.LOGIN, new byte[]{3, 4, 6, 3, 1, 6});
        Assert.assertArrayEquals(packet.getAmmendedData(), new byte[]{1, 0, 1, 3, 4, 6, 3, 1, 6});
    }

    @Test
    public void ensureNoBytesMatchDataSeparator() throws UnsupportedEncodingException {
        byte[] bytes = "asdfghjkl;'zxcvbnm,./][poiuytrewq\\+_-=)0(9*&^%$#@!~`12345678{}|:\"<>? ".getBytes("UTF-8");
        for (byte b : bytes)
            if (b == 0)
                Assert.fail();
    }

    @Test
    public void testDataSection() throws InvalidPacketException {
        Packet packet = new Packet(PacketType.LOGIN, new byte[]{0x2, 0x6, 0x0, 0x9, 0x4, 0x0, 0x5, 0x2, 0x3});
        Assert.assertArrayEquals(new byte[]{0x2, 0x6}, packet.getDataSection(0));
        Assert.assertArrayEquals(new byte[]{0x9, 0x4}, packet.getDataSection(1));
        Assert.assertArrayEquals(new byte[]{0x5, 0x2, 0x3}, packet.getDataSection(2));
    }
}