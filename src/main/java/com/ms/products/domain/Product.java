package com.ms.products.domain;

public class Product {
	private long id;
	private String name;
	private double price;
	private String category;
	private Spec specs;

	public Product() {
		super();
	}

	public Product(long id, String name, double price, String category, Spec specs) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.specs = specs;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Spec getSpecs() {
		return specs;
	}

	public void setSpecs(Spec specs) {
		this.specs = specs;
	}

}
