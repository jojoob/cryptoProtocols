package de.jojoob.dsa;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by joo on 19.02.15.
 */
public class DSA {

//	public key
	private BigInteger p;
	private BigInteger q;
	private BigInteger g;
	private BigInteger y;

//	private key
	private BigInteger x;

	public void generateKeyPair() {
		Random random = new Random();
		int l = 512;

//		calculate q
		BigInteger q = BigInteger.probablePrime(160, random);
		this.q = q;

//		calculate p
		System.out.println("searching p...");
		BigInteger p;
		int i = 0;
		do {
			i++;
			p = new BigInteger(l, random);
//			p = BigInteger.probablePrime(l, random); // we need less rounds but it takes longer to generate a prime number
			p = p.subtract(p.subtract(BigInteger.ONE).mod(q));
		} while (!p.isProbablePrime(100) || p.bitLength() != l);
		this.p = p;
		System.out.println("#rounds needed to find p: " + i);

//		calculate g
		BigInteger h;
		BigInteger g = BigInteger.ONE;
		BigInteger aux = p.subtract(BigInteger.ONE);
		BigInteger pow = aux.divide(q);
		do {
			h = new BigInteger(p.bitLength(), random);
			if (h.compareTo(BigInteger.ONE) == 1 && h.compareTo(p.subtract(BigInteger.ONE)) == -1) {
				g = h.modPow(pow, p);
			}
		} while (g.compareTo(BigInteger.ONE) == 0);
		this.g = g;

		BigInteger x;
		do {
			x = new BigInteger(q.bitLength(), random);
		} while (x.compareTo(BigInteger.ONE) != 1 && x.compareTo(q) != -1);
		this.x = x;

		BigInteger y = g.modPow(x, p);
		this.y = y;
	}

	public BigInteger[] getPublicKey() {
		BigInteger[] publicKey = {this.p, this.q, this.g, this.y};
		return publicKey;
	}

	public BigInteger[] sign(String m) throws NoSuchAlgorithmException {
		MessageDigest sha2= MessageDigest.getInstance("SHA-256");
		BigInteger sha2hash = new BigInteger(sha2.digest(m.getBytes()));

		Random random = new Random();
		BigInteger s;
		BigInteger s1 = BigInteger.ZERO;
		BigInteger s2 = BigInteger.ZERO;
		do {
			s = new BigInteger(this.q.bitLength(), random);
			if (s.compareTo(BigInteger.ONE) == 1 && s.compareTo(this.q) == -1) {
				s1 = this.g.modPow(s, this.p).mod(this.q);
				s2 = s.modInverse(this.q).multiply(sha2hash.add(s1.multiply(this.x))).mod(this.q);
			}
		} while (s1.compareTo(BigInteger.ZERO) == 0 || s2.compareTo(BigInteger.ZERO) == 0);

		BigInteger[] signature = {s1, s2};
		return signature;
	}

	public boolean verify(String m, BigInteger[] signatur, BigInteger[] publicKey) throws NoSuchAlgorithmException {
		MessageDigest sha2= MessageDigest.getInstance("SHA-256");
		BigInteger sha2hash = new BigInteger(sha2.digest(m.getBytes()));

		BigInteger s1 = signatur[0];
		BigInteger s2 = signatur[1];

		BigInteger p = publicKey[0];
		BigInteger q = publicKey[1];
		BigInteger g = publicKey[2];
		BigInteger y = publicKey[3];

		if (s1.compareTo(BigInteger.ZERO) == 1 && s1.compareTo(q) == -1 &&
				s2.compareTo(BigInteger.ZERO) == 1 && s2.compareTo(q) == -1) {
			BigInteger w = s2.modInverse(q);
			BigInteger u1 = sha2hash.multiply(w).mod(q);
			BigInteger u2 = s1.multiply(w).mod(q);
			BigInteger v = g.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);

			return v.compareTo(s1) == 0;
		}
		return false;
	}
}
