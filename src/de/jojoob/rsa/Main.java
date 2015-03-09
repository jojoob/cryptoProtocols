package de.jojoob.rsa;

import java.math.BigInteger;

/**
 * Created by joo on 19.02.15.
 */
public class Main {
	public static void main(String[] args) {
		RSA rsa = new RSA();
		rsa.generateKeyPair();
		BigInteger[] publicKey = rsa.getPublicKey();
		System.out.println("Public Key: e: " + publicKey[0] + ", n: " + publicKey[1]);

		BigInteger message = new BigInteger("7");
		System.out.println("Message: " + message);
		BigInteger ciphertext = rsa.encrypt(message, publicKey);
		System.out.println("Ciphertext: " + ciphertext);

		BigInteger ciphertextDecrypted = rsa.decrypt(ciphertext);
		System.out.println("Message': " + ciphertextDecrypted);

		BigInteger signature = rsa.sign(message);
		System.out.println("Signature': " + signature);

		BigInteger signatureVerified = rsa.verify(signature, publicKey);
		System.out.println("Message' (verified signature): " + signatureVerified);
	}
}
