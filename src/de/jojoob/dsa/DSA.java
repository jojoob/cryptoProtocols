package de.jojoob.dsa;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by joo on 19.02.15.
 */
// DSA Standard: http://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.186-4.pdf
public class DSA {

	MessageDigest messageDigest;

//	Bit length of p
	private int l;
//	Bit length of q
	private int n;

//	public key
	private BigInteger p;
	private BigInteger q;
	private BigInteger g;
	private BigInteger y;

//	private key
	private BigInteger x;

	public DSA() throws NoSuchAlgorithmException {
		this.messageDigest = MessageDigest.getInstance("SHA-256");
		this.l = 2048;
		this.n = 256;
//		other value combinations and hash functions are valid to: 1024/160, 2048/224, 2048/256, 3072/256
//		but consider if n is lower than bit length of used hash function, hash must cut to fit n
	}

	public void generateKeyPair() {
		Random random = new Random();

//		calculate q
		BigInteger q = BigInteger.probablePrime(n, random);
		this.q = q;

//		calculate p with p = k * q + 1 // p - 1 | q
		System.out.println("searching p...");
		BigInteger p;
		BigInteger k;
		int kLength = l - q.bitLength();
		int i = 0;
		do {
			i++;
			do {
				do {
					k = new BigInteger(kLength, random);
				} while (k.bitLength() == kLength);
				p = k.multiply(q).add(BigInteger.ONE);
			} while (p.bitLength() == l);
		} while (!p.isProbablePrime(100));
		this.p = p;
		System.out.println("#rounds needed to find p: " + i);

//		calculate g with order q
		BigInteger h;
		BigInteger g;
		do {
			do {
				h = new BigInteger(p.bitLength(), random);
			} while (h.compareTo(BigInteger.ONE) != 1 && h.compareTo(p.subtract(BigInteger.ONE)) != -1);
			g = h.modPow(k, p);
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

	public BigInteger[] sign(String m) {
		BigInteger sha2hash = new BigInteger(this.messageDigest.digest(m.getBytes()));

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

	public boolean verify(String m, BigInteger[] signatur, BigInteger[] publicKey) {
		BigInteger sha2hash = new BigInteger(this.messageDigest.digest(m.getBytes()));

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
