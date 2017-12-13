package com.hothouse.encripttool.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES的区块长度固定为128，支持 128, 192 和 256 位（16、24、32个字符）的秘钥
 * Android 运行环境支持 128、192、256 秘钥长度
 * Java jre 默认只支持 128（16字符）秘钥，如需支持 192,256，需要安装 JCE
 */

public class AESUtil {

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

    /**
     *
     * @param aesMode AES加密类型
     * @param ivStr 偏移
     * @param data 加密或解密数据
     * @param key 秘钥
     * @param mode 加密/解密模式
     * @return
     * @throws Exception
     */
    private static byte[] cipherOperation(String aesMode,String ivStr,byte[] data,byte[] key,int mode)throws Exception{
        if(key.length!=16&&key.length!=24&&key.length!=32)throw new Exception("The keys of AES supports only 128, 192 and 256 bits");
        Cipher cipher = Cipher.getInstance(aesMode);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        if(aesMode.contains("ECB")){
            cipher.init(mode, secretKey);
        }else {
            if(ivStr.length()!=16)throw new Exception("IV size of AES-128 should be 16 bytes");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivStr.getBytes());
            cipher.init(mode, secretKey, ivParameterSpec);
        }

        return cipher.doFinal(data);
    }
}
