package com.ms.products.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.products.domain.Product;
import com.ms.products.domain.ProductDto;
import com.ms.products.domain.Review;
import com.ms.products.repository.ProductRepository;
import com.sun.el.stream.Stream;

@Service
public class ProductService {

	@Autowired
	ProductRepository produtDao;

	@Autowired
	private RestTemplate restTemplate;

	public CompletableFuture<Product> save(Product product) {
		return CompletableFuture.supplyAsync(() -> produtDao.save(product));
	}

	public List<ProductDto> getProductWithReview() {
		List<ProductDto> productWithReviews = new ArrayList<ProductDto>();
		List<Product> products = produtDao.findAll();

		for (Product product : products) {
			ProductDto dto = new ProductDto();
			dto.setProduct(product);
			dto.setReviews(fectchReviews(product.getId()));
			productWithReviews.add(dto);
		}
		return productWithReviews;
	}

	public List<Product> findAll() {
		return produtDao.findAll();
	}

	public CompletableFuture<ProductDto> getProduct(long productId) {
		return CompletableFuture.supplyAsync(() -> produtDao.getProdcut(productId)).thenApply(product -> {
			ProductDto dto = new ProductDto();
			dto.setProduct(product);
			List<Review> reviews = fectchReviews(productId);
			product.setRating(reviews.stream().mapToDouble(Review::getRating).average().getAsDouble());
			dto.setReviews(reviews);
			return dto;
		});

	}

	private List<Review> fectchReviews(long id) {
		try {
			URI uri = URI.create("http://review-service/products/" + id + "/reviews");
			String result = restTemplate.getForObject(uri, String.class);
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(result, new TypeReference<List<Review>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Review>(1);
		}
	}

	public List<Review> reliable(long id) {
		return new ArrayList<Review>(1);
	}
	
	public void update(Product product) {
		produtDao.update(product);
	}
}
