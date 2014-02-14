package org.xman;

import java.util.Map;

public class ProductCreator202 {
	private Map<String, Product> products;

	public void setProducts(Map<String, Product> products) {
		this.products = products;
	}

	public Product createProduct(String productId) {
		Product product = products.get(productId);
		if (product != null) {
			return product;
		}
		throw new IllegalArgumentException("Unknown product");
	}
}
