package me.jesensky.dan.playertracker;

import me.jesensky.dan.playertracker.exceptions.NoSuchKeyException;

import java.io.*;
import java.util.HashMap;

/**
 * This class represents a configurable file, even though it
 * may not always be saved to a file, nor loaded from one.
 * Usability includes storing values in a {@code HashMap},
 * which may only be one-to-one with their keys; i.e., the key
 * {@code K} cannot have values of both {code V} and {@code M};
 * instead, the one which is stored after the other will replace the
 * other value.<br />
 * <br />
 * Implementations may use {@link #save(String, Configuration)} to
 * save a Configuration to a given file, while they may use
 * {@link #load(String)} to load from a given file.<br />
 * <br />
 * Serialization and deserialization are based on the UID
 * {@code 16632074508205}.
 */
public class Configuration implements Serializable {
    private static final long serialVersionUID = 16632074508205L;
    private HashMap<String, Object> values;

    /**
     * Instantiates a new {@code Configuration} instance. Does nothing
     * besides initialize the values.<br />
     * <br />
     * For loading a configuration, one should instead use {@link #load(String)}
     *
     * @see #load(String)
     */
    public Configuration(){
        super();
        this.values = new HashMap<String, Object>();
    }

    /**
     * Serializes the given {@code Configuration} instance, then writes
     * it to file, given the file name. Serialization is based on the
     * UID {@code 16632074508205}.
     *
     * @param filename The file to save to.
     * @param clazz The {@code Configuration} instance to save using.
     * @throws IOException When a {@code FileOutputStream} or
     * {@code ObjectOutputStream} either cannot be instantiated or fail to write.
     */
    public static void save(String filename, Configuration clazz) throws IOException {
        FileOutputStream f;
        ObjectOutputStream o = new ObjectOutputStream(f = new FileOutputStream(filename));
        o.writeObject(clazz);
        f.close();
        o.close();
    }

    /**
     * Deserialization is based on the UID {@code 16632074508205}.
     * @param filename The file to load a {@code Configuration}
     *                 instance from.
     * @return The instance of {@code Configuration} which was
     * loaded.
     *
     * @throws IOException If the file cannot be found, or is
     * already being used.
     */
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

    /**
     * Gets the value of the key given within this {@code Configuration}
     * instance's value listing. If the key does not exist, the given
     * {@code defaultVal} will be returned instead.
     *
     * @param key The key to retrieve the value of
     *
     * @param defaultVal The value to return if the given {@code key}
     *                   does not exist
     * @param <E> The expected type of the returned value
     *
     * @return The value stored at {@code key}, if it exists; otherwise,
     * {@code defaultVal} will be returned.
     */
    public <E> E getValue(String key, E defaultVal) {
        return this.containsKey(key) ? (E)this.values.get(key) : defaultVal;
    }

    /**
     * Retrieves the value at the specified {@code key}. If the
     * {@code key} represents no value, a {@code NoSuchKeyException}
     * will be thrown instead.
     *
     * @param key The key to retrieve the value of
     * @param <E> The expected type of the value at {@code key}
     * @return The value at {@code key}, if it exists
     * @throws NoSuchKeyException If the given {@code key} does not exist.
     */
    public <E> E getValue(String key) throws NoSuchKeyException {
        if(!this.containsKey(key))
            throw new NoSuchKeyException();
        return (E) this.values.get(key);
    }

    /**
     * Assigns the value of the given {@code key} to {@code value}. As the
     * parity of values should be one-to-one, this will cause any existing
     * value at the location of {@code key} to be overwritten.
     *
     * @param key The key to put the {@code value} at.
     * @param value The value to put at the {@code key}'s location.
     * @param <E> The type of the {@code value}.
     */
    public <E> void setValue(String key, E value) {
        this.values.put(key, value);
    }

    /**
     * Checks whether or not the given {@code key} exists in this
     * {@code Configuration}'s value listing.
     *
     * @param key The key to look for.
     * @return {@code true} if the key is present and represents a value;
     * {@code false} otherwise.
     */
    public boolean containsKey(String key){
        return this.values.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return "Configuration@"+super.hashCode()+"[size="+this.values.size()+"]";
    }
}