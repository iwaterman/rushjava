package org.xman.jnp4.uri;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;

// A program to download password-protected web pages
// http://bbs.pcbeta.com/member.php?mod=logging&action=login
public class SecureSourceViewer {

	public static void main(String args[]) {

		Authenticator.setDefault(new DialogAuthenticator());

		for (int i = 0; i < args.length; i++) {
			try {
				// Open the URL for reading
				URL u = new URL(args[i]);
				try (InputStream in = new BufferedInputStream(u.openStream())) {
					// chain the InputStream to a Reader
					Reader r = new InputStreamReader(in);
					int c;
					while ((c = r.read()) != -1) {
						System.out.print((char) c);
					}
				}
			} catch (MalformedURLException ex) {
				System.err.println(args[0] + " is not a parseable URL");
			} catch (IOException ex) {
				System.err.println(ex);
			}

			// print a blank line to separate pages
			System.out.println();
		}

		// Since we used the AWT, we have to explicitly exit.
		System.exit(0);
	}
}