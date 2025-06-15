package com.example.demo;

import com.example.demo.products.model.CartItem;
import com.example.demo.products.model.Product;

import static io.restassured.RestAssured.given;

import com.example.demo.products.repository.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ShopApplicationTests {

	@Autowired
	private ProductRepository productRepository;
	private static String authenticatedSession = null;
	private final Product product1 = new Product("123-abc", "laptop", "expensive laptop", new BigDecimal(1230));
	private final Product product2 = new Product("456-def", "mouse", "simple mouse", new BigDecimal(45));
	private final Product product3 = new Product("789-ghi", "monitor", "standard monitor", new BigDecimal(600));

	@BeforeEach
	public void init(){
		RestAssured.baseURI = "http://localhost:" + 8080;
		RestAssured.urlEncodingEnabled = false;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		resetProducts();

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

	@Test
	@WithMockUser(username="user")
	void getByIdAndDeleteProductTest() {

		Product byId = given().sessionId(authenticatedSession()).contentType(ContentType.JSON)
						.queryParam("id", product1.getId())
						.when()
						.get("/api/products/byId")
						.then()
						.statusCode(200).extract().as(Product.class);

		assertNotNull(byId);

		given().sessionId(authenticatedSession()).contentType(ContentType.JSON)
						.param("id", byId.getId())
						.when()
						.delete("/api/products/")
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

		assertEquals(2, products.size());

	}

	@Test
	@WithMockUser(username="user")
	void shoppingCartTest() {
		//Let's add one product1 to the users cart
		given().sessionId(authenticatedSession()).contentType(ContentType.JSON)
						.body(product1)
						.when()
						.post("/api/cart/")
						.then()
						.statusCode(200);

		//Let's add another product1 to the users cart
		given().sessionId(authenticatedSession()).contentType(ContentType.JSON)
						.body(product1)
						.when()
						.post("/api/cart/")
						.then()
						.statusCode(200);

		//Let's add another product1 to the users cart
		given().sessionId(authenticatedSession()).contentType(ContentType.JSON)
						.body(product2)
						.when()
						.post("/api/cart/")
						.then()
						.statusCode(200);

		//Let's check that the product is on the cart
		List<CartItem> cart =
						Arrays.asList(given().sessionId(authenticatedSession()).contentType(ContentType.JSON)
										.when()
										.get("/api/cart/")
										.then()
										.statusCode(200)
										.extract()
										.as(CartItem[].class));

		assertEquals(2, cart.size());

		//We have one product2 in the cart
		assertEquals(product2.getSku(), cart.get(0).getSku());
		assertEquals(1, cart.get(0).getAmount());

		//We have two product1 in the cart
		assertEquals(product1.getSku(), cart.get(1).getSku());
		assertEquals(2, cart.get(1).getAmount());


		//Let's check that the product is on the cart
		BigDecimal total =
						given().sessionId(authenticatedSession()).contentType(ContentType.JSON)
										.when()
										.get("/api/cart/total")
										.then()
										.statusCode(200)
										.extract()
										.as(BigDecimal.class);

		// The total is the multiplication of two product1 plus one product2
		assertEquals(product1.getPrice().multiply(new BigDecimal(2)).add(product2.getPrice()), total);
	}



	private String authenticatedSession() {
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


	private void resetProducts(){
		List<Product> all = productRepository.findAll();
		for(Product p : all){
			productRepository.deleteById(p.getId());
		}
		// save some products
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);
	}


}
