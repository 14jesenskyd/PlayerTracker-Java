package me.jesensky.dan.playertracker.net;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public final class NetUtils {
    public static byte[] byteListToArray(List<Byte> bytes){
        byte[] b = new byte[bytes.size()];
        for(int i = 0; i < b.length; i++)
            b[i] = bytes.get(i);
        return b;
    }

    public static byte[] getMD5Hash(byte[] b){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(b);
            return md5.digest();
        }catch(NoSuchAlgorithmException e){
            //ignore, md5 is always there
        }
        return null;
    }
}
