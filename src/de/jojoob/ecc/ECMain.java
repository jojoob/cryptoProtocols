package de.jojoob.ecc;

/**
 * Created by joo on 03.03.15.
 */
public class ECMain {
	public static void main(String[] args) {

		System.out.println("EC (double)");

		EC ec = new EC(-1, 1);

		EC.Point p = ec.newPoint(3, 5);
		EC.Point q = ec.newPoint(5, 11);
		EC.Point r;

		System.out.println("Point P("+p.x+"|"+p.y+")");
		System.out.println("Point Q("+q.x+"|"+q.y+")");
		System.out.println();

//		add
		r = p.add(q);
		System.out.println("Add: P + Q = R("+r.x+"|"+r.y+")");
		System.out.println();

//		doubling
		r = ec.doubling(p);
		System.out.println("Doubling: 2 * P = R("+r.x+"|"+r.y+")");
		System.out.println();

//		scalar
		int k = 50;
		System.out.println("k = "+k);
		r = ec.scalar(p, k);
		System.out.println("Scalar: k * P = R("+r.x+"|"+r.y+")");
		System.out.println();

	}
}
