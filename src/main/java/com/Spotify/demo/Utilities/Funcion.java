package com.Spotify.demo.Utilities;

import org.apache.tomcat.util.codec.binary.Base64;

public class Funcion {

    public static String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return  sb.toString();
    }

    public static String strToBase64(String str){
        //byte[] bytesEncoded = Base64.encodeBase64String(str.getBytes());
        return Base64.encodeBase64String(str.getBytes());
    }


}
