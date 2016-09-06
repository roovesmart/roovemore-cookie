package com.appspot.roovemore.cookie;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

//TODO To move to another project.
/**
 * Encryption and decryption using the Blowfish.
 *
 */
public class CookieBlowfish {

	/**
	 * It encrypts a string.
	 * @param key
	 * 			Encrypts key.
	 * @param text
	 * 			Encrypts string.
	 */
	protected static String enc(String key, String text) {

		try {
			SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, sksSpec);
			byte[] encrypted = cipher.doFinal(text.getBytes());

			return DatatypeConverter.printHexBinary(encrypted);

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	/**
	 * It decrypt a string.
	 * @param key
	 * 			Decrypt key.
	 * @param text
	 * 			Decrypt string.
	 */
	protected static String dec(String key, String text) {

		byte[] encrypted = null;

		try {
			encrypted = DatatypeConverter.parseHexBinary(text);
			SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, sksSpec);
			byte[] decrypted = cipher.doFinal(encrypted);
			return new String(decrypted);

		} catch (Exception e) {
			return null;
		}

	}

}
