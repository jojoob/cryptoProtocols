package de.jojoob.dh;

import de.jojoob.clientserver.InputProcessor;
import de.jojoob.clientserver.Server;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by joo on 05.03.15.
 */
public class MainDHServer {
	private static Server server;
	private static DHHost dhServer;

	public static void main(String[] args) throws IOException {

		server = new Server(31337);

		server.continuousRead(new ServerInputProcessor());

		dhServer = new DHHost();
//		dhServer.generateGPrandom(1024);
//		dhServer.generateGPDSALike(1024, 256);
		dhServer.generateGPsavePrime(1024);
		dhServer.generateA();

		server.writeLine(dhServer.getP().toString());
		server.writeLine(dhServer.getG().toString());
		server.writeLine(dhServer.getA().toString());

	}

	private static class ServerInputProcessor implements InputProcessor {
		@Override
		public void input(String input) {
//			System.out.println(input);
			BigInteger bigInteger = new BigInteger(input);

			dhServer.setB(bigInteger);

			System.out.println(dhServer.getK().toString(16));
		}
	}
}
