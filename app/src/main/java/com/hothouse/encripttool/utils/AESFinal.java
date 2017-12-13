package com.hothouse.encripttool.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2017/12/13.
 */

public class AESFinal {

    private static final String IV_STRING = "0000000000000000";
    private static final String CHARSET = "UTF-8";
    private static final String AES_MODE = "AES/CBC/PKCS5Padding";


    public static String encript(String data,String key)throws Exception{

       byte[] encriptedByteArr = cipherOperation(AES_MODE,IV_STRING,data.getBytes(CHARSET),key.getBytes(CHARSET),Cipher.ENCRYPT_MODE);

       return new String(Base64.encode(encriptedByteArr,Base64.NO_WRAP));
    }
    public static String encript(String aesMode,String ivStr,String data,String key)throws Exception{

        byte[] encriptedByteArr = cipherOperation(aesMode,ivStr,data.getBytes(CHARSET),key.getBytes(CHARSET),Cipher.ENCRYPT_MODE);

        return new String(Base64.encode(encriptedByteArr,Base64.NO_WRAP));
    }

    public static String decript(String data,String key)throws Exception{
        byte[] decriptedByteArr = cipherOperation(AES_MODE,IV_STRING,data.getBytes(CHARSET),key.getBytes(CHARSET),Cipher.DECRYPT_MODE);

        return new String(Base64.encode(decriptedByteArr,Base64.NO_WRAP));
    }

    public static String decript(String aesMode,String ivStr,String data,String key)throws Exception{
        byte[] decriptedByteArr = cipherOperation(aesMode,ivStr,data.getBytes(CHARSET),key.getBytes(CHARSET),Cipher.DECRYPT_MODE);

        return new String(Base64.encode(decriptedByteArr,Base64.NO_WRAP));
    }
    public static String decriptBase64Data(String aesMode,String ivStr,String base64Data,String key)throws Exception{
        byte[] data = Base64.decode(base64Data,Base64.NO_WRAP);
        byte[] decriptedByteArr = cipherOperation(aesMode,ivStr,data,key.getBytes(CHARSET),Cipher.DECRYPT_MODE);

        return new String(decriptedByteArr);
    }

    private static byte[] cipherOperation(String aesMode,String ivStr,byte[] data,byte[] key,int mode)throws Exception{
        if(key.length!=16)throw new Exception("秘钥长度需为16");
        Cipher cipher = Cipher.getInstance(aesMode);

        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        if(aesMode.contains("ECB")){
            cipher.init(mode, secretKey);
        }else {
            if(ivStr.length()!=16)throw new Exception("IV长度需为16");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivStr.getBytes());
            cipher.init(mode, secretKey, ivParameterSpec);
        }

        return cipher.doFinal(data);
    }
}
