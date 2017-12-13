package com.hothouse.encripttool.utils;


import android.util.Base64;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * 注意：此工具类使用 PKCS8 私钥
 * PKCS1 私钥生成：openssl genrsa -out rsa_private_key.pem 1024
 * PKCS1 转 PKCS8私钥：openssl pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform pem -nocrypt -out pkcs8_private_key.pem
 */
public class RSA {
	/** 指定key的大小 */
	private static final int KEYSIZE = 1024;
	private static final String CHAR_ENCODING = "UTF-8";
	private static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";

	public static class RSAKeyPair{
		public String publicKey;
		public String privateKey;
		public String modulus;
	}

	/**
	 * 生成密钥对
	 * 私钥格式为 PKCS8
	 */
	public static RSAKeyPair generateKeyPair() throws Exception {
		/** RSA算法要求有一个可信任的随机数源 */
		SecureRandom sr = new SecureRandom();
		/** 为RSA算法创建一个KeyPairGenerator对象 */
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		/** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
		kpg.initialize(KEYSIZE, sr);
		/** 生成密匙对 */
		KeyPair kp = kpg.generateKeyPair();
		/** 得到公钥 */
		Key publicKey = kp.getPublic();
		byte[] publicKeyBytes = publicKey.getEncoded();
		String pub = new String(Base64.encode(publicKeyBytes,Base64.NO_WRAP), CHAR_ENCODING);
		/** 得到私钥 */
		Key privateKey = kp.getPrivate();
		byte[] privateKeyBytes = privateKey.getEncoded();
		String pri = new String(Base64.encode(privateKeyBytes,Base64.NO_WRAP), CHAR_ENCODING);

		RSAKeyPair rsaKeyPair = new RSAKeyPair();
		rsaKeyPair.publicKey = pub;
		rsaKeyPair.privateKey = pri;

		RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
		BigInteger bint = rsp.getModulus();
		byte[] b = bint.toByteArray();
		byte[] deBase64Value = Base64.encode(b,Base64.NO_WRAP);
		String retValue = new String(deBase64Value);
		rsaKeyPair.modulus = retValue;
		return rsaKeyPair;
	}

	/**
	 * 加密方法 source： 源数据
	 */
	public static String encrypt(String source, String publicKey)
			throws Exception {
		Key key = getPublicKey(publicKey);
		/** 得到Cipher对象来实现对源数据的RSA加密 */
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] b = source.getBytes();
		/** 执行加密操作 */
		byte[] b1 = cipher.doFinal(b);
		return new String(Base64.encode(b1,Base64.NO_WRAP), CHAR_ENCODING);
	}

	/**
	 * 解密算法 cryptograph:密文
	 */
	public static String decrypt(String cryptograph, String privateKey)
			throws Exception {
		Key key = getPrivateKey(privateKey);
		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] b1 = Base64.decode(cryptograph.getBytes(),Base64.NO_WRAP);
		/** 执行解密操作 */
		byte[] b = cipher.doFinal(b1);
		return new String(b);
	}

	/**
	 * 得到公钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
				Base64.decode(key.getBytes(),Base64.NO_WRAP));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
				Base64.decode(key.getBytes(),Base64.NO_WRAP));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static String sign(String content, String privateKey) {
		String charset = CHAR_ENCODING;
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(privateKey.getBytes(),Base64.NO_WRAP));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			Signature signature = Signature.getInstance("SHA1WithRSA");

			signature.initSign(priKey);
			signature.update(content.getBytes(charset));

			byte[] signed = signature.sign();

			return new String(Base64.encode(signed,Base64.NO_WRAP));
		} catch (Exception e) {

		}

		return null;
	}
	
	public static boolean checkSign(String content, String sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(publicKey,Base64.NO_WRAP);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			Signature signature = Signature
			.getInstance("SHA1WithRSA");
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes("utf-8") );
		
			boolean bverify = signature.verify( Base64.decode(sign,Base64.NO_WRAP) );
			return bverify;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}	

}