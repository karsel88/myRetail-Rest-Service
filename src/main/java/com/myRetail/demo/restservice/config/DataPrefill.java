package com.myRetail.demo.restservice.config;

import com.myRetail.demo.restservice.domain.Price;
import com.myRetail.demo.restservice.domain.Product;
import com.myRetail.demo.restservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataPrefill implements CommandLineRunner {
	@Autowired
	private ProductRepository productRepository;

	@Override
	public void run(String... args) throws Exception {
		Product product = new Product();
		product.setId(13860428);
		product.setName("The Big Lebowski (Blu-ray)");
		product.setPrice(new Price(13.49,"USD",null));

		productRepository.addNewProduct(product);
	}
}
