package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.exceptions.NoSuchKeyException;

import java.io.*;
import java.util.HashMap;

public class Configuration implements Serializable {
    private static final long serialVersionUID = 16632074508205L;
    private HashMap<String, Object> values;

    public Configuration(){
        super();
        this.values = new HashMap<String, Object>();
    }

    public static void save(String filename, Configuration clazz) throws IOException {
        FileOutputStream f;
        ObjectOutputStream o = new ObjectOutputStream(f = new FileOutputStream(filename));
        o.writeObject(clazz);
        f.close();
        o.close();
    }

    public static Configuration load(String filename) throws IOException {
        ObjectInputStream i = null;
        FileInputStream f = null;
        try {
            i = new ObjectInputStream(f = new FileInputStream(filename));
            return ((Configuration) i.readObject());
        } catch (ClassNotFoundException e) {
            //there's no real possible way for this to occur, so ignore it
        }finally{
            if(i != null)
                i.close();
            if (f != null)
                f.close();
        }
        return null;
    }

    public <E> E getValue(String key, E defaultVal) {
        return this.containsKey(key) ? (E)this.values.get(key) : defaultVal;
    }

    public <E> E getValue(String key) throws NoSuchKeyException {
        if(!this.containsKey(key))
            throw new NoSuchKeyException();
        return (E) this.values.get(key);
    }

    public <E> void setValue(String key, E value) {
        this.values.put(key, value);
    }

    public boolean containsKey(String s){
        return this.values.containsKey(s);
    }

    @Override
    public String toString(){
        return "Configuration@"+super.hashCode()+"[size="+this.values.size()+"]";
    }
}