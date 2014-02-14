package org.xman;

public class ProductCreator201 {
	public static Product createProduct(String productId) {
		if ("aaa".equals(productId)) {
			return new Battery("AAA", 2.5);
		} else if ("cdrw".equals(productId)) {
			return new Disc("CD-RW", 1.5);
		}
		throw new IllegalArgumentException("Unknown product");
	}
}
