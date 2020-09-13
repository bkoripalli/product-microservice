package com.ms.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {

	long id;
	long productId;
	User reviewer;
	String description;
	boolean helpful;
	int rating;

	public Review() {
		super();
	}

	public Review(Integer id, User reviewer, String description, boolean helpful, int rating) {
		super();
		this.id = id;
		this.reviewer = reviewer;
		this.description = description;
		this.helpful = helpful;
		this.rating = rating;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isHelpful() {
		return helpful;
	}

	public void setHelpful(boolean helpful) {
		this.helpful = helpful;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", productId=" + productId + ", reviewer=" + reviewer + ", description="
				+ description + ", helpful=" + helpful + ", rating=" + rating + "]";
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
