package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.exceptions.InvalidArgumentException;
import me.jesensky.dan.playertracker.net.Connection;

import java.net.Socket;

public class ClientConnection extends Connection {
    public ClientConnection(Socket sock) throws InvalidArgumentException{
        super(sock);
    }

    @Override
    public void readData() {

    }
}
