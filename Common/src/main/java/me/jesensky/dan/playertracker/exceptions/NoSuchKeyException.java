package me.jesensky.dan.playertracker.exceptions;

public class NoSuchKeyException extends Exception{
    public NoSuchKeyException() {
        super("Invalid key; no value corresponds.");
    }

    public NoSuchKeyException(String s) {
        super(s);
    }
}
