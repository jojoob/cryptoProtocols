package de.jojoob.ecc;

import java.math.BigInteger;

/**
 * Created by joo on 03.03.15.
 */
public class EC {
	//	y^2 = x^3 + ax + b
	private double a;
	private double b;

	public class Point {
		public double x;
		public double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public Point add(Point q) {
			double term1 = (q.y - this.y) / (q.x - this.x);
			double rx = term1 * term1 - this.x - q.x;
			double ry = term1 * (this.x - rx) - this.y;
			return new Point(rx, ry);
		}
	}

	public EC(double a, double b) {
		this.a = a;
		this.b = b;
	}

	public Point newPoint(double x, double y) {
		return new Point(x, y);
	}

	public Point doubling(Point p) {
		double term1 = (3 * p.x * p.x + this.a) / (2 * p.y);
		double rx = term1 * term1 - 2 * p.x;
		double ry = term1 * (p.x - rx) - p.y;
		return new Point(rx, ry);
	}

	public Point scalar(Point p, int k) {
//	public Point scalar(Point p, BigInteger k) {
		Point r = null;
//		(ceil(log2(k < 0 ? -k : k+1)))
		for (int i = 0; i < 10; i++) {
			if ((k & (1<<i)) != 0) {
//			if (k.testBit(i)) {
				if (r == null) {
					r = newPoint(p.x, p.y);
				} else {
					r = r.add(p);
				}
			}
			p = doubling(p);
		}
		return r;
	}


}
