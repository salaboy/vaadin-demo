package com.example.demo;

import com.example.demo.products.model.Product;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ShopApplicationTests {

	@BeforeEach
	public void init(){
		RestAssured.baseURI = "http://localhost:" + 8080;
		RestAssured.urlEncodingEnabled = false;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	private static String authenticatedSession = null;

	public String authenticatedSession() {
		if (authenticatedSession == null) {
			// init session
			authenticatedSession = RestAssured
							.given()
							.accept(ContentType.HTML)
							.when()
							.get("/login")
							.sessionId();

			// perform login
			authenticatedSession = RestAssured
							.given()
							.sessionId(authenticatedSession)
							.csrf("/login")
							.accept(ContentType.HTML)
							.contentType(ContentType.URLENC)
							.formParam("username", "user")
							.formParam("password", "password")
							.when()
							.post("/login")
							.sessionId();
		}
		return authenticatedSession;
	}

	@Test
	@WithMockUser(username="user")
	void listProductsTest() {
		List<Product> products = Arrays.asList(
						given().sessionId(authenticatedSession())
										.contentType(ContentType.JSON)
						.when()
						.get("/api/products/")
						.then()
						.statusCode(200)
										.extract()
										.as(Product[].class));

		assertEquals(3, products.size());


	}

	@Test
	@WithMockUser(username="user")
	void addProductTest() {
		Product oldCar = new Product("999-zzz", "old car", "vintage car", new BigDecimal(9999));
		given().sessionId(authenticatedSession()).contentType(ContentType.JSON)
						.body(oldCar)
						.when()
						.post("/api/products/")
						.then()
						.statusCode(200);

		List<Product> products = Arrays.asList(
						given().sessionId(authenticatedSession()).contentType(ContentType.JSON)
										.when()
										.get("/api/products/")
										.then()
										.statusCode(200)
										.extract()
										.as(Product[].class));

		assertEquals(4, products.size());
		Product oldCarFromEndpoint = products.get(3);
		assertEquals(oldCarFromEndpoint.getSku(), oldCar.getSku());
		assertEquals(oldCarFromEndpoint.getName(), oldCar.getName());
		assertEquals(oldCarFromEndpoint.getDescription(), oldCar.getDescription());


	}


}
