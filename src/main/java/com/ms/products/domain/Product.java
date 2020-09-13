package com.ms.products.domain;

import java.sql.Date;

public class Product {
	private long id;
	private String name;
	private String code;
	private double price;
	private Date releaseDate;
	private String category;
	private String description;
	private double rating;
	private Spec specs;
	private String imageName;

	public Product() {
		super();
	}

	public Product(long id, String name, String code, double price, Date releaseDate, String category,
			String description, double rating, Spec specs, String imageName) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.price = price;
		this.releaseDate = releaseDate;
		this.category = category;
		this.description = description;
		this.rating = rating;
		this.specs = specs;
		this.imageName = imageName;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Spec getSpecs() {
		return specs;
	}

	public void setSpecs(Spec specs) {
		this.specs = specs;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
