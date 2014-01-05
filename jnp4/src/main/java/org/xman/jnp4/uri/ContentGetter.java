package org.xman.jnp4.uri;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ContentGetter {

	public static void main(String[] args) {

		String link = "http://www.oreilly.com/";
		link = "http://www.oreilly.com/graphics_new/animation.gif";

		if (args.length > 0) {
			link = args[0];
		}
		// Open the URL for reading
		try {
			URL u = new URL(link);
			Object o = u.getContent();
			System.out.println("I got a " + o.getClass().getName());
		} catch (MalformedURLException ex) {
			System.err.println(args[0] + " is not a parseable URL");
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}