import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.packets.Packet;
import me.jesensky.dan.playertracker.net.packets.PacketType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
        Assert.assertArrayEquals(packet.getAmmendedData(), new byte[]{1,0,1,3,4,6,3,1,6});
    }
}