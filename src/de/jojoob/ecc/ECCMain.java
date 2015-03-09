package de.jojoob.ecc;

import java.math.BigInteger;

/**
 * Created by joo on 07.03.15.
 */
public class ECCMain {
	public static void main(String[] args) {

//		Curve Prime192v1
//		Parameter: http://csrc.nist.gov/groups/ST/toolkit/documents/dss/NISTReCur.pdf
		System.out.println("Curve Prime192v1:");
		BigInteger a = new BigInteger("-3");
		BigInteger b = new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1", 16);
		BigInteger p = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
		ECC ecc = new ECC(a, b, p);
		System.out.println("a: " + a);
		System.out.println("b: " + b.toString(16));
		System.out.println("p: " + p);
		System.out.println();

		System.out.println("Base Point:");
		BigInteger gX = new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012", 16);
		BigInteger gY = new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811", 16);
		ECC.Point g = ecc.newPoint(gX, gY);
		System.out.println(g.x.toString(16));
		System.out.println(g.y.toString(16));
		System.out.println();

//		Test Values: http://point-at-infinity.org/ecc/nisttv
		BigInteger k;
		for (int i = 1; i < 10; i++) {
			k = BigInteger.valueOf(i);

			System.out.println("Scalar k=" + k + " result:");
			ECC.Point scalarPoint = g.scalar(k);
			System.out.println(scalarPoint.x.toString(16));
			System.out.println(scalarPoint.y.toString(16));
			System.out.println();
		}
	}
}
