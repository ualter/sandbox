package br.ujr.sandbox.token.jwt;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenApp {
	
	public static void main(String[] args) throws Exception {
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		
		//WalletKeys walletKeys = loadKeysFromPfxCertificate();
		WalletKeys walletKeys = loadKeysFromCertificatePEMPrivateKeySeparated();
		
		String token = createSignedJWT(walletKeys);
		System.out.println("**********>> Signed JWT Token...\n" + token);
		
		DecodedJWT jwt = verifySignedJWT(walletKeys, token);
		System.out.println("\n**********>> Decoded JWT Token...");
		System.out.println("Issuer.......: " + jwt.getIssuer());
		System.out.println("Subject......: " + jwt.getSubject());
		System.out.println("IAT..........: " + jwt.getIssuedAt());
		System.out.println("Subject......: " + jwt.getSubject());
		System.out.println("Type.........: " + jwt.getType());
		System.out.println("Algorithm....: " + jwt.getAlgorithm());
		System.out.println("ContentType..: " + jwt.getContentType());
	}


	/**
	 * Load the Public and Private Key from a Binary format Certificate, PKCS12, .pfx extension
	 */
	private static WalletKeys loadKeysFromPfxCertificate() throws KeyStoreException, NoSuchProviderException,
			IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
		KeyStore keyStore = KeyStore.getInstance("pkcs12", "SunJSSE");
		keyStore.load(JwtTokenApp.class.getResourceAsStream("/serverCertificate.pfx"), "changeit".toCharArray());

		X509Certificate certificate = (X509Certificate)keyStore.getCertificate("1");
		PublicKey publicKey = certificate.getPublicKey();
		Key key = keyStore.getKey("1", "changeit".toCharArray());
		PrivateKey privateKey = (PrivateKey)key;
		
		WalletKeys walletKeys =  new WalletKeys(publicKey, privateKey);
		return walletKeys;
	}


	private static String createSignedJWT(WalletKeys walletKeys) {
		Algorithm algorithm = Algorithm.RSA256(
				(RSAPublicKey)walletKeys.getPublicKey(), 
				(RSAPrivateKey)walletKeys.getPrivateKey()
		);
		
		ZonedDateTime timeBarcelona = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
		Date expiryDateTime         = Date.from(timeBarcelona.plusHours(1).toInstant());
		Date currentDateTime        = Date.from(timeBarcelona.toInstant());
		//Date notBeforeDateTime      = Date.from(timeBarcelona.plusHours(2).toInstant());
		Date notBeforeDateTime      = currentDateTime;
		
		String token = JWT.create()
		                  .withIssuer("auth0")
		                  .withAudience("audience")
		                  .withSubject("Subject")
		                  .withClaim("scope", "read,write")
		                  .withIssuedAt(currentDateTime)
		                  .withExpiresAt(expiryDateTime)
		                  .withNotBefore(notBeforeDateTime)
		                  .sign(algorithm);
		return token;
	}
	
	private static DecodedJWT verifySignedJWT(WalletKeys walletKeys, String token) {
		Algorithm algorithm = Algorithm.RSA256(
				(RSAPublicKey)walletKeys.getPublicKey(), 
				(RSAPrivateKey)walletKeys.getPrivateKey()
		);
		
		JWTVerifier verifier = JWT.require(algorithm)
		                          .withIssuer("auth0")
		                          .withSubject("Subject")
		                          .build(); 
		DecodedJWT jwt = verifier.verify(token);
		return jwt;
	}


	private static void testKeys(WalletKeys walletKeys) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
		String  message   = "UALTER";
		String  signature = Security.sign(walletKeys.getPrivateKey(), message);
		boolean verify    = Security.verify(walletKeys.getPublicKey(), "UALTER", signature);
		
		StringBuffer sb = new StringBuffer("******** SIGN ********\n");
		sb.append("Signature...:").append(signature).append("\n");
		sb.append("Message.....:").append(message).append("\n");
		sb.append("Verify......:").append(verify).append("\n");
		System.out.println(sb.toString());
	}
	
	
	/**
	 * Load the Public Key from a PEM File (.crt extension -> serverCertificate.crt)
	 * Load the Private Key from another PEM File (.key extension -> privateKey.key)
	 */
	public static WalletKeys loadKeysFromCertificatePEMPrivateKeySeparated() throws Exception {
		CertificateFactory factory           = CertificateFactory.getInstance("X509");
		
		String             certificationFile   = "serverCertificate.crt";
		String             privateKeyFile      = "privateKey.key";
		
		// Load Certificate
		InputStream        streamToCertificate = JwtTokenApp.class.getResourceAsStream("/" + certificationFile);
		X509Certificate    x509Certificate     = (X509Certificate)factory.generateCertificate(streamToCertificate);
		
		// Load Public Key from Certificate
		PublicKey          publicKey           = x509Certificate.getPublicKey();
		

		// Load Private Key from Private Key file (.key)
		String privateKeyPEM = new String(Files.readAllBytes(Paths.get(JwtTokenApp.class.getResource("/" + privateKeyFile).toURI())));
		PemReader pemReader = new PemReader(new StringReader(privateKeyPEM));
		PemObject pemObject;
		try {
			pemObject = pemReader.readPemObject();
		} finally {
			pemReader.close();
		}
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    byte[] content = pemObject.getContent();
	    PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
	    RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
	    
	    return new WalletKeys(publicKey, privateKey);
	}
	
	
	
	

}
