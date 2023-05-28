package com.gateway.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



/**
 *
 * @author sekher AESEncryption uses AES Alogrithm with 128 Key size to emcrypt
 *         and decrypt data.
 *
 */
public class AESEncryption {

	public static int KEY_SIZE = 128;
	Cipher encryptCipher = null;
	Cipher decryptCipher = null;
	static AESEncryption instance = null;
	public static String encodedKey = "t7GcYbbdbKxZtV2ge6qpeQ==";

	public static AESEncryption getInstance() {
		return instance;
	}

	public static void init() throws Exception {
		instance = new AESEncryption(encodedKey, "AES/CBC/PKCS5Padding");
	}

	public AESEncryption(String encodedKey, String paddingString) throws Exception {
		byte key[] = Base64.getDecoder().decode(encodedKey);
		// byte key[] = fixSecret(encodedKey,16);
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		// encryptCipher = Cipher.getInstance("AES");
		encryptCipher = Cipher.getInstance(paddingString);

		encryptCipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(new byte[16]));
		// decryptCipher = Cipher.getInstance("AES");
		decryptCipher = Cipher.getInstance(paddingString);
		decryptCipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(new byte[16]));
	}

	public AESEncryption(String encodedKey) throws Exception {
		byte key[] = Base64.getDecoder().decode(encodedKey);
		// byte key[] = fixSecret(encodedKey,16);
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		encryptCipher = Cipher.getInstance("AES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, keySpec);
		decryptCipher = Cipher.getInstance("AES");
		decryptCipher.init(Cipher.DECRYPT_MODE, keySpec);
	}

	public String encode(String input) throws Exception {
		byte result[] = encryptCipher.doFinal(input.getBytes());
		String encodedResult = Base64.getEncoder().encodeToString(result);
		return encodedResult;
	}

	public String decode(String encodedInput) throws Exception {
		byte input[] = Base64.getDecoder().decode(encodedInput);
		// byte input[] = fixSecret(encodedInput,32);
		// logger.info("==== "+input.length);
		byte result[] = decryptCipher.doFinal(input);
		return new String(result, "utf-8");
	}

	private byte[] fixSecret(String s, int length) throws UnsupportedEncodingException {
		if (s.length() < length) {
			int missingLength = length - s.length();
			for (int i = 0; i < missingLength; i++) {
				s += " ";
			}
		}

		// logger.info("fixSecret :::"+s+"****"+(s.substring(0,
		// length).getBytes()));
		return s.substring(0, length).getBytes("UTF-8");
	}

	public static String generateKey() throws NoSuchAlgorithmException {
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(KEY_SIZE);
		SecretKey secertKey = generator.generateKey();
		byte key[] = secertKey.getEncoded();
		String encodedKey = Base64.getEncoder().encodeToString(key);
		return encodedKey;

	}

	public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password,
			MessageDigest md) {

		int digestLength = md.getDigestLength();
		int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
		byte[] generatedData = new byte[requiredLength];
		int generatedLength = 0;

		try {
			md.reset();

			// Repeat process until sufficient data has been generated
			while (generatedLength < keyLength + ivLength) {

				// Digest data (last digest if available, password data, salt if available)
				if (generatedLength > 0)
					md.update(generatedData, generatedLength - digestLength, digestLength);
				md.update(password);
				if (salt != null)
					md.update(salt, 0, 8);
				md.digest(generatedData, generatedLength, digestLength);

				// additional rounds
				for (int i = 1; i < iterations; i++) {
					md.update(generatedData, generatedLength, digestLength);
					md.digest(generatedData, generatedLength, digestLength);
				}

				generatedLength += digestLength;
			}

			// Copy key and IV into separate byte arrays
			byte[][] result = new byte[2][];
			result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
			if (ivLength > 0)
				result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

			return result;

		} catch (DigestException e) {
			throw new RuntimeException(e);

		} finally {
			// Clean out temporary data
			Arrays.fill(generatedData, (byte) 0);
		}
	}

	
	

}
