package de.jojoob.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by joo on 20.02.15.
 */
public class Main {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String text1 = "Hello Word";
		String text2 = "Computer in love";

		MessageDigest sha2 = MessageDigest.getInstance("SHA-256");
		MessageDigest md5 = MessageDigest.getInstance("MD5");

		System.out.println(text1);
		System.out.println("as SHA-256:");
		byte[] text1_sha2hash = sha2.digest(text1.getBytes());
//		System.out.println(Arrays.toString(text1_sha2hash));
		System.out.println(bytesToHex(text1_sha2hash));

		System.out.println("as MD5:");
		byte[] text1_md5hash = md5.digest(text1.getBytes());
		System.out.println(bytesToHex(text1_md5hash));


		System.out.println();

		System.out.println(text2);
		System.out.println("as SHA-256:");
		byte[] text2_sha2hash = sha2.digest(text2.getBytes());
		System.out.println(bytesToHex(text2_sha2hash));

		System.out.println("as MD5:");
		byte[] text2_md5hash = md5.digest(text2.getBytes());
		System.out.println(bytesToHex(text2_md5hash));

	}

	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
