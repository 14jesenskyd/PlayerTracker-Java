package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.exceptions.InvalidArgumentException;
import me.jesensky.dan.playertracker.exceptions.InvalidPacketException;
import me.jesensky.dan.playertracker.net.Connection;
import me.jesensky.dan.playertracker.net.NetUtils;
import me.jesensky.dan.playertracker.net.packets.LoginPacket;
import me.jesensky.dan.playertracker.net.packets.LoginResponsePacket;

import javax.swing.*;
import java.io.IOException;

public class ClientUI extends JFrame{
    public ClientUI(){
        super("Player Tracker");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setLayout(null);

        JLabel l = new JLabel("Username:");
        l.setSize(85, 15);
        l.setLocation(10, 10);
        super.getContentPane().add(l);

        JTextField t = new JTextField();
        t.setSize(120, 15);
        t.setLocation(l.getX()+l.getWidth(), l.getY());
        super.getContentPane().add(t);

        JLabel p = new JLabel("Password:");
        p.setSize(l.getWidth(), l.getHeight());
        p.setLocation(l.getX(), l.getY()+l.getHeight());
        super.getContentPane().add(p);

        JTextField z = new JTextField();
        z.setSize(t.getWidth(), t.getHeight());
        z.setLocation(t.getX(), p.getY());
        super.getContentPane().add(z);

        JButton logIn = new JButton("Log in");
        logIn.setLocation(p.getX(), p.getY()+p.getHeight()+3);
        logIn.setSize(80, 16);
        logIn.addActionListener((e) -> {
            try {
                //TODO remove hard-coded values, load from config
                Client.getClient().connect("::1", 1534);
                Connection c = Client.getClient().getConnection();
                LoginPacket packet = new LoginPacket(t.getText(), z.getText());
                packet.sendData(c);
                RequestManager req = Client.getClient().getRequestManager();
                while(!req.hasResponse());
                LoginResponsePacket x = ((LoginResponsePacket) req.getResponse());
                switch(x.getResponse()){
                    case SUCCESS:
                        //TODO remove debug code
                        JOptionPane.showMessageDialog(null, "Login succeeded");
                        new SearchUI();
                        break;
                    case FAILURE:
                        JOptionPane.showMessageDialog(this, "Login failed."+(x.hasDataSection(1) ? "\n\n"+ NetUtils.bytesToString(x.getDataSection(1)) : ""));
                        break;
                }

            }catch(IOException | InvalidArgumentException | InvalidPacketException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        super.getContentPane().add(logIn);

        //super.setSize(t.getWidth() + 10 + t.getX(), logIn.getHeight() + 10 + logIn.getY());
        super.setSize(225, 98);

        JButton exit = new JButton("Exit");
        exit.setSize(logIn.getWidth(), logIn.getHeight());
        exit.setLocation(super.getWidth() - 10 - exit.getWidth(), logIn.getY());
        exit.addActionListener((e) -> System.exit(0));
        super.getContentPane().add(exit);

        super.setResizable(false);
        super.setVisible(true);
    }

    @Override
    public String toString(){
        return super.toString();
    }
}