package de.jojoob.cert;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joo on 20.02.15.
 */
public class Main {
	public static void main(String[] args) throws CertificateException, IOException {
		String filename = "res/certs";
		FileInputStream fis = new FileInputStream(filename);
		BufferedInputStream bis = new BufferedInputStream(fis);

		CertificateFactory cf = CertificateFactory.getInstance("X.509");

		List<X509Certificate> certs = new ArrayList<X509Certificate>();
		while (bis.available() > 0) {
			X509Certificate cert = (X509Certificate)cf.generateCertificate(bis);
			certs.add(cert);
			System.out.println(cert.toString());


		}

		try {
			certs.get(1).verify(certs.get(0).getPublicKey());
			System.out.println("'Cert 1' correctly signed with 'Cert 0'");

			System.out.println("'Cert 1' valid until: " + certs.get(1).getNotAfter());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}

	}
}
