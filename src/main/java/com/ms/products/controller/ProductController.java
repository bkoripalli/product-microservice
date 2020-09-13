package com.ms.products.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.products.domain.Product;
import com.ms.products.domain.ProductDto;
import com.ms.products.repository.ProductRepository;
import com.ms.products.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductService service;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public CompletableFuture<Product> save(@RequestBody Product product) {
		CompletableFuture<Product> productFuture = service.save(product);
		System.out.println("Produt save call done" + Thread.currentThread().getName());
		return productFuture;
	}
	
	@PutMapping(path= "/{productId}", consumes = "application/json")
	public void update(@RequestBody Product product, @PathVariable("productId") long productId) {
		System.out.println(".."+productId);
		product.setId(productId);
		
		
		service.update(product);
	}

	@GetMapping(produces = "application/json")
	public List<Product> findAll() {
		return service.findAll();
	}

	@GetMapping(path = "/{productId}", produces = "application/json")
	public CompletableFuture<ProductDto> getProduct(@PathVariable("productId") long productId) {
		return service.getProduct(productId);
	}
}
