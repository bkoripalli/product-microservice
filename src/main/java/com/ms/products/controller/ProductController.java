package com.ms.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.products.domain.Product;
import com.ms.products.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Product> save(@RequestBody Product product) {
		return new ResponseEntity<Product>(productRepository.save(product), HttpStatus.OK);
	}
	
	@GetMapping
	public List<Product> findAll() {
		return productRepository.findAll();
		
	}
	
	
     
}
