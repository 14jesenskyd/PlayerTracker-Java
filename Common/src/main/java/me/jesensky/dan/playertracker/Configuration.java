package me.jesensky.dan.playertracker;

import java.util.HashMap;

public abstract class Configuration {
    private HashMap<String, Object> values;

    public abstract void load();

    public abstract void save();

    public abstract <E> E getValue(String key);

    public abstract <E> E getValue(String key, E defaultVal);

    public abstract <E> void setValue(String key, E value);
}