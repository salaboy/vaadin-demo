package com.example.demo.products.service;

import com.example.demo.products.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@SessionScope
@Component
public class CartService {
  private final List<Product> cart = new ArrayList<>();

  public void addProductToCart(Product product){
    cart.add(product);
  }

  public List<Product> getAllCartProducts(){
    return cart;
  }

}
