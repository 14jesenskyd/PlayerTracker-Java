package me.jesensky.dan.playertracker.net;


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public final class NetUtils {
    private NetUtils(){
    }

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

    public static byte[] stringToBytes(String s){
        try {
            return s.getBytes("UTF-8");
        }catch(UnsupportedEncodingException e){
            //if your computer does not have UTF-8, there's something severely wrong with it, or you're running windows 95.
        }
        return null;
    }

    public static String bytesToString(byte[] b){
        try{
            return new String(b, "UTF-8");
        }catch(UnsupportedEncodingException e){
            //stop running windows 95
        }
        return null;
    }
}
