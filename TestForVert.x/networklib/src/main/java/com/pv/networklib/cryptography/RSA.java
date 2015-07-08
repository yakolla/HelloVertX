package com.pv.networklib.cryptography;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {
	
	static public KeyPair CreateKeyPair() throws NoSuchAlgorithmException
	{
		
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(512);

    	return generator.genKeyPair();
		
	}
	
	static public RSAPublicKeySpec GetPublicKeySpec(KeyPair keyPair) throws NoSuchAlgorithmException, InvalidKeySpecException
	{	
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return (RSAPublicKeySpec) keyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);		
	}
	
	static public byte[] Encrypt(PublicKey publicKey, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{	
		Cipher cipher = Cipher.getInstance("RSA");;
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    	return cipher.doFinal(data);	
	}
	
	static public byte[] Encrypt(PublicKey publicKey, String data) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
	{
		return Encrypt(publicKey, data.getBytes("utf-8"));		
	}

	static public byte[] DecryptToByteArray(PrivateKey privateKey, byte[] encryptedData) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(encryptedData);		
	}		
	
	static public String DecryptToString(PrivateKey privateKey, byte[] encryptedData) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		return new String(DecryptToByteArray(privateKey, encryptedData), "utf-8");		
	}
}
