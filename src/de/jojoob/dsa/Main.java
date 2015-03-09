package de.jojoob.dsa;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

/**
 * Created by joo on 19.02.15.
 */
public class Main {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		DSA dsa = new DSA();
		dsa.generateKeyPair();

		String message = "Hello World!";

		BigInteger[] signature = dsa.sign(message);
		System.out.println("Signature: " + signature[0] + ", " + signature[1]);
		boolean verify = dsa.verify(message, signature, dsa.getPublicKey());
		System.out.println(verify);
	}
}
