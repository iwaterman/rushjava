package org.xman.jnp4.addr;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyAddress {

	public static void main(String[] args) {
		try {
			InetAddress address = InetAddress.getLocalHost();
			System.out.println(address);
			
			InetAddress me = InetAddress.getLocalHost();
			String dottedQuad = me.getHostAddress();
			System.out.println("My address is " + dottedQuad);
				
		} catch (UnknownHostException ex) {
			System.out.println("Could not find this computer's address.");
		}
	}
}
