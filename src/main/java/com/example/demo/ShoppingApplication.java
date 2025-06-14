package com.example.demo;

import com.example.demo.products.model.Product;
import com.example.demo.products.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class ShoppingApplication {

	private static final Logger log = LoggerFactory.getLogger(ShoppingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(ProductRepository repository) {
		return (args) -> {
			// save a couple of products
			repository.save(new Product("123-abc", "laptop", "expensive laptop", new BigDecimal(1230)));
			repository.save(new Product("456-def", "mouse", "simple mouse", new BigDecimal(45)));
			repository.save(new Product("789-ghi", "monitor", "standard monitor", new BigDecimal(600)));

			// fetch all products
			log.info("Products found with findAll():");
			log.info("-------------------------------");
			for (Product p : repository.findAll()) {
				log.info(p.toString());
			}
			log.info("");


		};
	}
}
