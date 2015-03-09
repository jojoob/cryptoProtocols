package de.jojoob.ecc;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class PointTest {
	ECC ecc;
	ECC.Point g;

	@Before
	public void setUp() throws Exception {
//		Curve Prime192v1
//		Parameter: http://csrc.nist.gov/groups/ST/toolkit/documents/dss/NISTReCur.pdf
		BigInteger a = new BigInteger("-3");
		BigInteger b = new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1", 16);
		BigInteger p = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
		ecc = new ECC(a, b, p);

		BigInteger gX = new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012", 16);
		BigInteger gY = new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811", 16);
		g = ecc.newPoint(gX, gY);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testScalar1() throws Exception {
		BigInteger k = new BigInteger("1");
		BigInteger expectedX = new BigInteger("188DA80EB03090F67CBF20EB43A18800F4FF0AFD82FF1012", 16);
		BigInteger expectedY = new BigInteger("07192B95FFC8DA78631011ED6B24CDD573F977A11E794811", 16);

		calculateCheck(k, expectedX, expectedY);
	}

	@Test
	public void testScalar2() throws Exception {
		BigInteger k = new BigInteger("2");
		BigInteger expectedX = new BigInteger("DAFEBF5828783F2AD35534631588A3F629A70FB16982A888", 16);
		BigInteger expectedY = new BigInteger("DD6BDA0D993DA0FA46B27BBC141B868F59331AFA5C7E93AB", 16);

		calculateCheck(k, expectedX, expectedY);
	}

	@Test
	public void testScalar3() throws Exception {
		BigInteger k = new BigInteger("3");
		BigInteger expectedX = new BigInteger("76E32A2557599E6EDCD283201FB2B9AADFD0D359CBB263DA", 16);
		BigInteger expectedY = new BigInteger("782C37E372BA4520AA62E0FED121D49EF3B543660CFD05FD", 16);

		calculateCheck(k, expectedX, expectedY);
	}

	@Test
	public void testScalar20() throws Exception {
		BigInteger k = new BigInteger("20");
		BigInteger expectedX = new BigInteger("BB6F082321D34DBD786A1566915C6DD5EDF879AB0F5ADD67", 16);
		BigInteger expectedY = new BigInteger("91E4DD8A77C4531C8B76DEF2E5339B5EB95D5D9479DF4C8D", 16);

		calculateCheck(k, expectedX, expectedY);
	}

	@Test
	public void testScalarBig1() throws Exception {
		BigInteger k = new BigInteger("112233445566778899");
		BigInteger expectedX = new BigInteger("81E6E0F14C9302C8A8DCA8A038B73165E9687D0490CD9F85", 16);
		BigInteger expectedY = new BigInteger("F58067119EED8579388C4281DC645A27DB7764750E812477", 16);

		calculateCheck(k, expectedX, expectedY);
	}

	@Test
	public void testScalarBig2() throws Exception {
		BigInteger k = new BigInteger("112233445566778899112233445566778899");
		BigInteger expectedX = new BigInteger("B357B10AC985C891B29FB37DA56661CCCF50CEC21128D4F6", 16);
		BigInteger expectedY = new BigInteger("BA20DC2FA1CC228D3C2D8B538C2177C2921884C6B7F0D96F", 16);

		calculateCheck(k, expectedX, expectedY);
	}

	@Test
	public void testScalarBig3() throws Exception {
		BigInteger k = new BigInteger("1618292094200346491064154703205151664562462359653015613567");
		BigInteger expectedX = new BigInteger("74FEC215F253C6BD845831E059B318C87F727B136A700B91", 16);
		BigInteger expectedY = new BigInteger("4B702B15B126A703E7A7CEC3E0EC81F8DFCA73A59F5D88B9", 16);

		calculateCheck(k, expectedX, expectedY);
	}

	private void calculateCheck(BigInteger k, BigInteger expectedX, BigInteger expectedY) {
		ECC.Point result = this.g.scalar(k);
		assertEquals(expectedX, result.x);
		assertEquals(expectedY, result.y);
	}
}