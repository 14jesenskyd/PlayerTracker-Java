package me.jesensky.dan.playertracker.exceptions;

/**
 * Represents when a packet contains invalid data,
 * or could not be constructed correctly.
 */
public class InvalidPacketException extends Exception {
    public InvalidPacketException() {
        super("Invalid packet! Perhaps the header is malformed, or the byte data does not constitute a packet of that type?");
    }

    public InvalidPacketException(String s) {
        super(s);
    }
}