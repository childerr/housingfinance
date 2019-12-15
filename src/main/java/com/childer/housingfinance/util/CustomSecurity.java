package com.childer.housingfinance.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomSecurity {
    public String encSHA256(String text) {
        StringBuffer buf = new StringBuffer();
        MessageDigest mDigest;

        try {
            mDigest = MessageDigest.getInstance("SHA-256");
            mDigest.update(text.getBytes());

            byte[] msgStr = mDigest.digest() ;
            for(int i=0; i < msgStr.length; i++){
                byte tmpStrByte = msgStr[i];
                String tmpEncTxt = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);

                buf.append(tmpEncTxt) ;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return buf.toString();
    }
}
