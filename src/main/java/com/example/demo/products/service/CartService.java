package com.example.demo.products.service;

import com.example.demo.products.model.CartItem;
import com.example.demo.products.model.Product;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This bean is session scoped so it will be stored in the HTTPSession
 */
@SessionScope
@Component
public class CartService {
  private final Map<String, Product> cartProducts = new HashMap<>();
  private final Map<String, Integer> cartSKUAmount = new HashMap<>();

  public void addProductToCart(Product product){
    cartProducts.put(product.getSku(), product);

    if (cartSKUAmount.containsKey(product.getSku())){
      Integer amount = cartSKUAmount.get(product.getSku());
      cartSKUAmount.put(product.getSku(), amount + 1);
    }else{
      cartSKUAmount.put(product.getSku(), 1);
    }

  }



  public List<CartItem> getAllCartItem(){
    List<CartItem> cartItems = new ArrayList<>(cartSKUAmount.size());
    for(String sku : cartSKUAmount.keySet()) {
      cartItems.add(new CartItem(cartProducts.get(sku), cartSKUAmount.get(sku)));
    }
    return cartItems;
  }

  public BigDecimal cartTotal(){
    BigDecimal total = new BigDecimal(0);
    for(String sku : cartSKUAmount.keySet()){
      total = total.add(cartProducts.get(sku).getPrice()
              .multiply(new BigDecimal(cartSKUAmount.get(sku))));
    }
    return total;
  }



}
