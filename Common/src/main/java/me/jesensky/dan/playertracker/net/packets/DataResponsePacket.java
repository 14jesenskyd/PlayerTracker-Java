package me.jesensky.dan.playertracker.net.packets;

import me.jesensky.dan.playertracker.UserViolationLevel;
import me.jesensky.dan.playertracker.exceptions.InvalidArgumentException;
import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.NetUtils;

/**
 * Represents a response to a data fetch packet.<br />
 * <br />
 * Contains, in specific order:
 * <ol>
 * <li>Player name, with exact capitalization of the
 * name stored in the database,</li>
 * <li>Notes, if any,</li>
 * <li>Infractions, if any; and finally,</li>
 * <li>The user's violation level.</li>
 * </ol>
 * <br />
 * For easier access, one may use methods to access these
 * without using
 * {@link me.jesensky.dan.playertracker.net.packets.Packet}'s
 * members.
 *
 * @see #getName()
 * @see #getNotes()
 * @see #getViolations()
 * @see #getViolationLevel()
 */
public class DataResponsePacket extends Packet {
    public DataResponsePacket(byte... data) throws InvalidPacketException {
        super(PacketType.DATA_RESPONSE, data);
    }

    public String getName() {
        return NetUtils.bytesToString(super.getDataSection(0));
    }

    public String getNotes() {
        return NetUtils.bytesToString(super.getDataSection(1));
    }

    public String getViolations() {
        return NetUtils.bytesToString(super.getDataSection(2));
    }

    public UserViolationLevel getViolationLevel() throws InvalidPacketException {
        try {
            return UserViolationLevel.getViolationLevelFromByte(super.getDataSection(3)[0]);
        } catch (InvalidArgumentException e) {
            throw new InvalidPacketException(e.getMessage());
        }
    }
}