package com.example.demo;

import com.example.demo.products.controller.ProductsRestController;
import com.example.demo.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private ProductRepository repository;

	@Test
	void contextLoads() {

	}

}
