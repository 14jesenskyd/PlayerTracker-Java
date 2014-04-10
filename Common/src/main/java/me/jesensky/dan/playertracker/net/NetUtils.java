package me.jesensky.dan.playertracker.net;


import java.util.List;

public final class NetUtils {
    public static byte[] byteListToArray(List<Byte> bytes){
        byte[] b = new byte[bytes.size()];
        for(int i = 0; i < b.length; i++)
            b[i] = bytes.get(i);
        return b;
    }
}
