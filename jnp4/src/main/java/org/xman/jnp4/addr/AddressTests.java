package org.xman.jnp4.addr;

import java.net.InetAddress;

//Determining whether an IP address is v4 or v6
public class AddressTests {

	public static int getVersion(InetAddress ia) {
		byte[] address = ia.getAddress();
		if (address.length == 4)
			return 4;
		else if (address.length == 16)
			return 6;
		else
			return -1;
	}
}