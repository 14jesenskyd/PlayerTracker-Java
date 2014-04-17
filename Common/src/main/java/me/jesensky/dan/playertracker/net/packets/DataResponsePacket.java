package me.jesensky.dan.playertracker.net.packets;

import me.jesensky.dan.playertracker.UserViolationLevel;
import me.jesensky.dan.playertracker.exceptions.InvalidArgumentException;
import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.NetUtils;

public class DataResponsePacket extends Packet{
    public DataResponsePacket(byte... data) throws InvalidPacketException{
        super(PacketType.DATA_RESPONSE, data);
    }

    public String getName(){
        return NetUtils.bytesToString(super.getDataSection(0));
    }

    public String getNotes(){
        return NetUtils.bytesToString(super.getDataSection(1));
    }

    public String getViolations(){
        return NetUtils.bytesToString(super.getDataSection(2));
    }

    public UserViolationLevel getViolationLevel() throws InvalidPacketException{
        try {
            return UserViolationLevel.getViolationLevelFromByte(super.getDataSection(3)[0]);
        }catch(InvalidArgumentException e){
            throw new InvalidPacketException(e.getMessage());
        }
    }
}