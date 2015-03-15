package de.jojoob.rsa;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by joo on 19.02.15.
 */
public class RSA {

//	public key
	private BigInteger n;
	private BigInteger e;

//	private key
	private BigInteger d;

	public void generateKeyPair() {
		Random random = new Random();
		BigInteger p = BigInteger.probablePrime(1024, random);

		BigInteger q;
		BigInteger n;
		do {
			q = BigInteger.probablePrime(1024, random);
			n = p.multiply(q); // Note: multiply two 1024 bit values the result is 2047 or 2048 bit long
		} while (n.bitLength() != 2048);

		this.n = n;

		BigInteger phin = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

		BigInteger e = BigInteger.valueOf(65537);
		this.e = e;

		BigInteger d = e.modInverse(phin);
		this.d = d;
	}

	/**
	 *
	 * @return Public key as an array of size 2 where e is at index 0 and n at index 1.
	 */
	public BigInteger[] getPublicKey() {
		BigInteger[] publicKey = {e, n};
		return publicKey;
	}

	/**
	 *
	 * @param message
	 * @param publicKey Public key to enrypt message with.
	 * @return Ciphertext
	 */
	public BigInteger encrypt(BigInteger message, BigInteger[] publicKey) {
		return message.modPow(publicKey[0], publicKey[1]);
	}

	/**
	 *
	 * @param ciphertext
	 * @return Message
	 */
	public BigInteger decrypt(BigInteger ciphertext) {
		return ciphertext.modPow(this.d, this.n);
	}

	/**
	 *
	 * @param message
	 * @return Signature
	 */
	public BigInteger sign(BigInteger message) {
		return message.modPow(this.d, this.n);
	}

	/**
	 *
	 * @param signature
	 * @param publicKey Public key related to the signature.
	 * @return Message
	 */
	public BigInteger verify(BigInteger signature, BigInteger[] publicKey) {
		return signature.modPow(publicKey[0], publicKey[1]);
	}

}
