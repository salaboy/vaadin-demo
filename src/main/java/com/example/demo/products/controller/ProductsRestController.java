package com.example.demo.products.controller;

import com.example.demo.products.model.Product;
import com.example.demo.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
public class ProductsRestController {
  @Autowired
  private ProductService productService;

  @PostMapping("/products/")
  public Product saveProduct(@RequestBody Product product){
    return productService.saveProduct(product);
  }

  @GetMapping("/products/{id}")
  public Product getProductById(@RequestParam("id") String productId){
    return productService.getProductById(productId);
  }

  @DeleteMapping("/products/{id}")
  public void deleteProduct(@RequestParam("id") String productId){
    productService.deleteProduct(productId);
  }

  @GetMapping("/products/")
  public Iterable<Product> getProducts(){
    return productService.getProducts();
  }


}
