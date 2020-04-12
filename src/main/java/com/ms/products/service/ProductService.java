package com.ms.products.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.products.domain.Product;
import com.ms.products.domain.ProductDto;
import com.ms.products.domain.Review;
import com.ms.products.repository.ProductRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ProductService {

	@Autowired
	ProductRepository produtDao;

	@Autowired
	private RestTemplate restTemplate;

	public List<ProductDto> findAll() {
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
}
