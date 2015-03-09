package de.jojoob.ecc;

import java.math.BigInteger;

/**
 * Created by joo on 03.03.15.
 */
public class ECC {
//	y^2 = x^3 + ax + b mod p
	private BigInteger a;
	private BigInteger b; // isn't needed for calculation actually, just listed for integrity.
	private BigInteger p;

	public class Point {
		public BigInteger x;
		public BigInteger y;

		public Point(BigInteger x, BigInteger y) {
			this.x = x;
			this.y = y;
		}

		public Point add(Point q) {
//			"'cause infinity is difficult to calculate"
			if(q.x.equals(BigInteger.ZERO) && q.y.equals(BigInteger.ZERO)){
				return new Point(this.x, this.y);
			}

//			BigInteger term1 = q.y.subtract(this.y).divide(q.x.subtract(this.x));
			BigInteger term1 = q.y.subtract(this.y).multiply(q.x.subtract(this.x).modInverse(ECC.this.p));
			BigInteger rx = term1.pow(2).subtract(this.x).subtract(q.x).mod(ECC.this.p);
			BigInteger ry = term1.multiply(this.x.subtract(rx)).subtract(this.y).mod(ECC.this.p);
			return new Point(rx, ry);
		}

		public Point doubling() {
//			BigInteger term1 = BigInteger.valueOf(3).multiply(this.x.pow(2)).add(ECC.this.a).divide(BigInteger.valueOf(2).multiply(this.y));
			BigInteger term1 = BigInteger.valueOf(3).multiply(this.x.pow(2)).add(ECC.this.a).multiply(BigInteger.valueOf(2).multiply(this.y).modInverse(ECC.this.p));
			BigInteger rx = term1.pow(2).subtract(BigInteger.valueOf(2).multiply(this.x)).mod(ECC.this.p);
			BigInteger ry = term1.multiply(this.x.subtract(rx)).subtract(this.y).mod(ECC.this.p);
			return new Point(rx, ry);
		}

		public Point scalar(BigInteger k) {
			Point tmp = this;
			Point r = new Point(BigInteger.ZERO, BigInteger.ZERO);
			for (int i = 0; i < k.bitLength(); i++) {
				if (k.testBit(i)) {
					r = tmp.add(r);
				}
				tmp = tmp.doubling();
			}
			return r;
		}
	}

	public ECC(BigInteger a, BigInteger b, BigInteger p) {
		this.a = a;
		this.b = b;
		this.p = p;
	}

	public Point newPoint(BigInteger x, BigInteger y) {
		return new Point(x, y);
	}
}
