package me.jesensky.dan.playertracker.exceptions;

public class InvalidArgumentException extends Exception{
    public InvalidArgumentException() {
        super("Invalid argument!");
    }

    public InvalidArgumentException(String s) {
        super(s);
    }
}
