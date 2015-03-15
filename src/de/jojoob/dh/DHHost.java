package de.jojoob.dh;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by joo on 05.03.15.
 */
public class DHHost {
	Random random;
	private BigInteger p;
	private BigInteger g;
	private BigInteger a;
	private BigInteger k;

	public DHHost() {
		this.random = new Random();
	}

	public void generateGPrandom(int pLength) {
		this.p = BigInteger.probablePrime(pLength, this.random);
		this.g = new BigInteger(p.bitLength(), this.random).mod(p);
	}

	public void generateGPDSALike(int pLength) {
		int qLength = 160; // size of subgroup order

//		choose q (as subgroup order)
		BigInteger q = BigInteger.probablePrime(qLength, random);

//		calculate p
		BigInteger p;
		do {
			p = new BigInteger(pLength, this.random);
			p = p.subtract(p.subtract(BigInteger.ONE).mod(q));
		} while (!p.isProbablePrime(100) || p.bitLength() != pLength);
		this.p = p;

//		calculate g
		BigInteger h;
		BigInteger g = BigInteger.ONE;
		BigInteger aux = p.subtract(BigInteger.ONE);
		BigInteger pow = aux.divide(q);
		do {
			h = new BigInteger(p.bitLength(), this.random);
			if (h.compareTo(BigInteger.ONE) == 1 && h.compareTo(p.subtract(BigInteger.ONE)) == -1) {
				g = h.modPow(pow, p);
			}
		} while (g.compareTo(BigInteger.ONE) == 0);
		this.g = g;
	}

	public void generateGPsavePrime(int pLength) {
		this.g = new BigInteger("2");
		BigInteger u;
		BigInteger p;
		do {
			u = BigInteger.probablePrime(pLength-1, this.random);
			p = g.multiply(u).add(BigInteger.ONE);
		} while (!p.isProbablePrime(100));
		this.p = p;
	}

	public void generateA() {
		this.a = new BigInteger(this.p.bitLength(), this.random).mod(p);
	}

	public BigInteger getP() {
		return this.p;
	}

	public BigInteger getG() {
		return this.g;
	}

	public void setP(BigInteger p) {
		this.p = p;
	}

	public void setG(BigInteger g) {
		this.g = g;
	}

	public BigInteger getA() {
		// Note: ga represents A
		BigInteger ga = this.g.modPow(this.a, this.p);
		return ga;
	}

	// Note: gb represents B
	public void setB(BigInteger gb) {
		this.k = gb.modPow(this.a, this.p);
	}

	public BigInteger getK() {
		return this.k;
	}
}
