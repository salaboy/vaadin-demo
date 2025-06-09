package com.example.demo.products.service;

import com.example.demo.products.model.Product;
import com.example.demo.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductService {
   @Autowired
   private ProductRepository productRepository;

  public Product saveProduct( Product product){
    return productRepository.save(product);
  }

  public Product getProductById(String productId){
    return productRepository.findById(productId).get();
  }

  public void deleteProduct(String productId){
    productRepository.deleteById(productId);
  }

  public Iterable<Product> getProducts(){
    return productRepository.findAll();
  }
}
