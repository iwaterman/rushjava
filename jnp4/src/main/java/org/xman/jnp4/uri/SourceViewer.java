package org.xman.jnp4.uri;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class SourceViewer {

	public static void main(String[] args) {

		String link = "http://www.baidu.com";
		if (args.length > 0) {
			link = args[0];
		}

		InputStream in = null;
		try {
			// Open the URL for reading
			URL u = new URL(link);
			in = u.openStream();
			// buffer the input to increase performance
			in = new BufferedInputStream(in);
			// chain the InputStream to a Reader
			Reader r = new InputStreamReader(in);
			int c;
			while ((c = r.read()) != -1) {
				System.out.print((char) c);
			}
		} catch (MalformedURLException ex) {
			System.err.println(link + " is not a parseable URL");
		} catch (IOException ex) {
			System.err.println(ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}
}