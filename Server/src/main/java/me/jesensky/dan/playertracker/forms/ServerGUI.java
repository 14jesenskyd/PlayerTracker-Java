package me.jesensky.dan.playertracker.forms;

import me.jesensky.dan.playertracker.Server;

import javax.swing.*;
import java.awt.*;

public class ServerGUI extends JFrame {
    private JLabel lblConnections;
    private JButton btnShowAllConnections;
    private transient FontMetrics metrics;

    public ServerGUI() {
        super("Player Tracker Server");
        super.setSize(500, 500);
        super.setLayout(null);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String t;

        this.metrics = super.getFontMetrics(new Font("Dialog", Font.PLAIN, 12));

        t = "Current connections: ";
        JLabel label = new JLabel(t);
        label.setLocation(25, 50);
        label.setSize(this.metrics.stringWidth(t) + 6, this.metrics.getHeight());
        super.getContentPane().add(label);


        t = "0";
        this.lblConnections = new JLabel(t);
        this.lblConnections.setLocation(label.getX() + label.getWidth(), label.getY());
        this.lblConnections.setSize(this.metrics.stringWidth(t) + 6, label.getHeight());
        super.getContentPane().add(this.lblConnections);


        this.metrics = super.getFontMetrics(new Font("Dialog", Font.BOLD, 12));
        t = "Show All Connections...";
        this.btnShowAllConnections = new JButton(t);
        this.btnShowAllConnections.setSize(this.metrics.stringWidth(t) + 36, this.metrics.getHeight() + 2);
        this.btnShowAllConnections.setLocation(this.lblConnections.getX() + this.lblConnections.getWidth(), this.lblConnections.getY());
        this.btnShowAllConnections.setEnabled(false);
        this.btnShowAllConnections.addActionListener((evt) -> {
            if (Server.getSingleton().getConnections().isEmpty())
                JOptionPane.showMessageDialog(this, "No connections exist to view.", "No Connections", JOptionPane.ERROR_MESSAGE);
            else
                super.add(new ConnectionListGUI());
        });
        super.getContentPane().add(this.btnShowAllConnections);

        super.setVisible(true);
    }

    public void setConnections(int amount) {
        String f = String.format("%,.0d", amount);
        this.lblConnections.setText(f);
        this.lblConnections.setSize(this.metrics.stringWidth(f) + 6, this.lblConnections.getHeight());
        this.btnShowAllConnections.setLocation(this.lblConnections.getX() + this.lblConnections.getWidth(), this.lblConnections.getY());
        if (amount == 0)
            this.btnShowAllConnections.setEnabled(false);
        else
            this.btnShowAllConnections.setEnabled(true);
    }
}
