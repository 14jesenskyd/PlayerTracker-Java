package me.jesensky.dan.playertracker.exceptions;

public class InvalidPacketException extends Exception{
    public InvalidPacketException(){
        super("Invalid packet! Perhaps the header is malformed, or the byte data does not constitute a packet of that type?");
    }

    public InvalidPacketException(String s){
        super(s);
    }
}