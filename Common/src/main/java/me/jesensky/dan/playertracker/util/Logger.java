package me.jesensky.dan.playertracker.util;

import me.jesensky.dan.playertracker.TimeUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class Logger {
    private FileWriter log;
    private String filename;
    private final String TIME_FORMAT = "hh:mm:ss p";

    public Logger(String filename) throws IOException{
        super();
        this.filename = filename;
        this.log = new FileWriter(filename);
    }

    public void info(String s){
        this.write(Level.INFO, s);
    }

    public void warning(String s){
        this.write(Level.WARNING, s);
    }

    public void error(String s){
        this.write(Level.SEVERE, s);
    }

    private void write(Level logLevel, String text){
        try {
            this.log.write(String.format("[%s][%s] %s", TimeUtils.getTime(this.TIME_FORMAT), logLevel.getName(), text));
        }catch(IOException e){
            //print to console -- there's not really much you can do about it
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String toString(){
        return "Logger["+this.filename+"]";
    }
}