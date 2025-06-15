# Shop Demo

Simple Vaadin + Redis Demo

- Shop REST APIS 
- Shop Product List stored in Redis using Spring Data Redis
- Testcontainers Redis + RestAssured for testing
- Shop UI Vaadin
- Spring Security + Vaadin Login Form

## Run and test

To start the application run: 

```sh
mvn spring-boot:test-run
```
You can access the application pointing your browser to: [http://localhost:8080](http://localhost:8080)

Users:
- "user":"password"
- "admin":"password"

You can run all tests with:
```sh
mvn clean install
```

## What's done: 
- Vaadin App using native spring services for Product List, Shopping Cart, Sessions List
- Using Spring Security and Vaadin integration for Login page
- Simple HTTP endpoints for Product List and Cart
- Cart is being stored in the HTTP Session using @SessionScoped beans (unfortunately this doesn't work with Vaadin and Redis, for session replication/storage)
- Using Testcontainers to interact with a Redis Instance
- Using RestAssured to test HTTP endpoints, as the Vaadin app is not using the HTTP endpoints
- HTTP Session List just shows HTTP Sessions

## What's missing: 
- Session replication with Redis (`spring-session-data-redis`), this can be done if we remove Vaadin and just use HTTP endpoints
- Search capabilities, as spring data redis supports a limited number of keywords for the ListCrudRepository


## Issues & references: 

- Spring Security and Vaadin: https://github.com/vaadin/flow/issues/21685#issuecomment-2972735608 
- Spring Data Session + Redis + Vaadin issues: https://github.com/vaadin/flow/issues/21669
- Spring Data Redis doesn't support Like or Contains derivations: https://github.com/spring-projects/spring-data-redis/issues/1719