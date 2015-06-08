package de.jojoob.feige_fiat_shamir;

import de.jojoob.clientserver.InputProcessor;
import de.jojoob.clientserver.Server;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Feige-Fiat-Shamir Server
 */
public class FFSServer {

	/**
	 * Rounds of Feige-Fiat-Shamir protocol within one interactive communication.
	 */
	public static int rounds = 100;

	private static Server server;
	private static int state = 0;
	private static final int RECEIVE_X = 0;
	private static final int RECEIVE_Y = 1;
	private static final int READY = 100;
	private static int count = 0;

	private static SecureRandom rnd = new SecureRandom();

	/**
	 * public modulus
	 * n = p * q
	 */
	public static BigInteger n = new BigInteger("454000216043172537257412785885387218236562822184920529428914623376154139547676074967672335160254428261451508111613591012676078526771532555933218131656227260600475923751060223960173350725174108738090083416620607505566640049332429935449835174298125237664345840775334612328690930762671178146585373173134292215875290076820133371745509275998112177429570857252541585406304131678995851944534349357607598528274192782701603145163835390120304368869380208427620741205233477453606169429489543773586671773407220451191112338589883511192455552177222879894945071043773364588898473743959976477327601386675805634762462911116813288796392830630674037866111679976868093423478624103502916140402400334265958661246324243829635746738109377258798550150298127834498907852879585016468637449920063007492233336396053190753598442788209446510529022182789428702653983578159229258955666207924698222424835967743584186117911266628865087131985065693278725225174794337098123308720445879614728156983787013385510233273876033948914056349071041318448306424559387278642754144029329810713344589068849227189688577663417127866499418274860198825227643683632236927496842295850609199873271215590655855158972073212784517983687585032597618647918227148737178248839727154400314077100987");
	/**
	 * public key
	 * v = s^2 mod n
	 */
	public static BigInteger v = new BigInteger("54520624243336813965556222292238342823785457546565616774698470999054841181618259215262837248317829035048811284241871706782350800131760914128240343997605031791386515120339468986219931672026133326264725182038722148063424476413080489554623862839778649453608041744109014591196278396927375982314423009273525719280376305876699277366450401500146704193726766824047951846313095790744506590033457254048619333984752113764035429299659295407493487995796424109064040482070807655203607835979664579428280765274700060851648832983959792359865714455613424812633014534315325472483836011058053527161381885552624917639537347382072289573354939498216933593173177795300128092392715368475875503307219049223877948339003766681537264268838873437037549818597472545195159790353107728431841398230448696224176651363049102187124364830374352695669687570063931374682875472664843365042873692844471810818341867633066739349895182065602158271495933328107107056291193203542639587291553388450140221109956887775124076198678899418501272877414070304860007678478663072073496331483564811257392301586689150571217756402990780778491409946176472024043400044378783332131663990600748896676858753605695675847863391379050297848012737140597730096864644040219151617089486708462676844163666");

	private static List<BigInteger> xList = new ArrayList<BigInteger>(rounds);
	private static List<BigInteger> yList = new ArrayList<BigInteger>(rounds);
	private static List<Boolean> bList = new ArrayList<Boolean>(rounds);

	public static void main(String[] args) throws IOException {
		server = new Server(31337);
		server.continuousRead(new ServerInputProcessor());
	}

	private static class ServerInputProcessor implements InputProcessor {
		@Override
		public void input(String input) {
			switch (state) {
				case RECEIVE_X:
					xList.add(new BigInteger(input));
					count++;
					if (count >= rounds) {
						String bListString = "";
						for (int i = 0; i < rounds; i++) {
							bList.add(new Boolean(FFSServer.rnd.nextBoolean()));

							if (bList.get(i) == true) {
								bListString += "1";
							} else {
								bListString += "0";
							}
						}

						System.out.println("bListString: " + bListString);
						try {
							server.writeLine(bListString);
						} catch (IOException e) {
							e.printStackTrace();
						}
						count = 0;
						state = RECEIVE_Y;
					}
					break;
				case RECEIVE_Y:
					yList.add(new BigInteger(input));
					count++;
					if (count >= rounds) {
						try {
							if (verify()) {
								server.writeLine("Glueckwunsch, koof dir 'n Eis. ;)");
							} else {
								server.writeLine("Aetschibaetsch! :P");
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						state = READY;
					}
					break;
				default:
					break;
			}
			if (state == READY) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Verify Client's results from all rounds.
	 * @return Result of verification. true if all round checks pass, otherwise false.
	 */
	private static boolean verify() {
		System.out.println("verify");
		for (int i = 0; i < rounds; i++) {
			BigInteger test = yList.get(i).modPow(BigInteger.valueOf(2), n);
			BigInteger proof;
			if (bList.get(i) == false) {
				proof = xList.get(i);
			} else {
				proof = xList.get(i).multiply(v).mod(n);
			}
			if (!test.equals(proof)) {
				return false;
			}
		}
		return true;
	}
}
