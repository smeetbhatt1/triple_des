package com.smeetbhatt.triple_des;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TripleDESCrypto {

    public String encrypt(String unencryptedString, String key)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
    {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] digestOfPassword = md.digest(key.getBytes("UTF-16LE"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

        for (int j = 0, k = 16; j < 8;)                                      //pad key for desired length
        {
            keyBytes[k++] = keyBytes[j++];
        }

        SecretKey secretKey = new SecretKeySpec(keyBytes, 0, 24, "DESede");  //Specifies Key spec
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);               //Specifies IV parameter spec
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");       //Specifies the type if Padding utilised
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        byte[] plainTextBytes = unencryptedString.getBytes("UTF-16LE");      //UTF-16LE to maintain compatibility from .NET services
        byte[] buf = cipher.doFinal(plainTextBytes);

        String base64EncryptedString = Base64.encodeToString(buf, Base64.DEFAULT);      //Encode into readable format for URLs

        return base64EncryptedString;
    }

    public String decrypt(String message, String key) throws Exception
    {
        byte[] messageBytes = Base64.decode(message, Base64.DEFAULT);      //Decode text from Base64 readable encoding.

        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] digestOfPassword = md.digest(key.getBytes("UTF-16LE"));  //UTF-16LE ENCODING IS MANDATORY
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

        for (int j = 0, k = 16; j < 8;)                                 //Key padding to reach desired size.
        {
            keyBytes[k++] = keyBytes[j++];
        }

        SecretKey skey = new SecretKeySpec(keyBytes, "DESede");         //Provide secret key spec
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);          //Provide IV spec
        Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");//Provide Cipher padding spec
        decipher.init(Cipher.DECRYPT_MODE, skey, iv);

        byte[] plainText = decipher.doFinal(messageBytes);

        return cleand(new String(plainText));                                   //DO NOT USE tostring() as it provides decoded(to native utf8) and hence, useless values.
    }

    private String cleand(String res2)
    {
        char[]  x = res2.toCharArray();
        char[] y = new char[x.length];
        int j=0;
        String abc = "";
        for(int i=0;i<res2.length();i++)            //Loop to clean the decrypted string of unwanted spaces.
        {
            if(i%2==0)
            {
                y[j]=x[i];
                abc += x[i];
                j++;
            }
        }

        String res3 = new String(y);                //Do not use toString() function as it changes encoding giving garbage values.
        return abc;
    }

}