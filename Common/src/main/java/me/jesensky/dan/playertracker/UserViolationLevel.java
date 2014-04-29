package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.exceptions.InvalidArgumentException;

public enum UserViolationLevel {
    GOOD((byte) 0x1),
    WARN((byte) 0x2),
    SEVERE((byte) 0x3),
    BANNED((byte) 0x4);
    private byte ident;

    UserViolationLevel(byte ident) {
        this.ident = ident;
    }

    public static UserViolationLevel getViolationLevelFromByte(byte b) throws InvalidArgumentException {
        for (UserViolationLevel l : UserViolationLevel.values()) {
            if (l.getByteIdentity() == b)
                return l;
        }
        throw new InvalidArgumentException("Byte " + b + " does not correspond to a UserViolationLevel.");
    }

    public byte getByteIdentity() {
        return this.ident;
    }
}