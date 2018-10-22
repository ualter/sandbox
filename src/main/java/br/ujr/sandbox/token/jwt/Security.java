package br.ujr.sandbox.token.jwt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

import javax.crypto.Cipher;

public class Security {
	

	public Security() {
	}
	
	
	public static String sign(PrivateKey privateKey, String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
	    Signature sign = Signature.getInstance("SHA1withRSA");
	    sign.initSign(privateKey);
	    sign.update(message.getBytes("UTF-8"));
	    return new String(Base64.getEncoder().encodeToString(sign.sign()));
	}


	public static boolean verify(PublicKey publicKey, String message, String signature) throws SignatureException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
	    Signature sign = Signature.getInstance("SHA1withRSA");
	    sign.initVerify(publicKey);
	    sign.update(message.getBytes("UTF-8"));
	    return sign.verify(Base64.getDecoder().decode(signature.getBytes("UTF-8")));
	}

	public static String encrypt(String rawText, PublicKey publicKey) throws IOException, GeneralSecurityException {
	    Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	    return Base64.getEncoder().encodeToString(cipher.doFinal(rawText.getBytes("UTF-8")));
	}

	public static String decrypt(String cipherText, PrivateKey privateKey) throws IOException, GeneralSecurityException {
	    Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.DECRYPT_MODE, privateKey);
	    return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)), "UTF-8");
	}


}
