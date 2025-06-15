package com.example.demo.products.controller;

import com.example.demo.products.model.CartItem;
import com.example.demo.products.model.Product;
import com.example.demo.products.repository.ProductRepository;
import com.example.demo.products.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class ProductsRestController {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CartService cartService;

  @PostMapping("/products/")
  public Product saveProduct(@RequestBody Product product){
    return productRepository.save(product);
  }

  @GetMapping("/products/byId")
  public Product getProductById(@RequestParam("id") String productId){
    Optional<Product> byId = productRepository.findById(productId);
    return byId.orElse(null);
  }


  @DeleteMapping("/products/")
  public void deleteProduct(@RequestParam("id") String productId){
    productRepository.deleteById(productId);
  }

  @GetMapping("/products/")
  public List<Product> getProducts(){
    return productRepository.findAll();
  }

  // Invalidate the session
  @GetMapping("/session/invalidate")
  public String invalidateSession(HttpSession session) {
    // Invalidate the session
    session.invalidate();
    return "Session invalidated!";
  }

  //@TODO: move this to a separate controller
  @PostMapping("/cart/")
  public void addProductToShoppingCart(@RequestBody Product product) {
    cartService.addProductToCart(product);
  }

  //@TODO: move this to a separate controller
  @GetMapping("/cart/")
  public List<CartItem> getShoppingCartProducts(){
    return cartService.getAllCartItem();
  }

  //@TODO: move this to a separate controller
  @GetMapping("/cart/total")
  public BigDecimal getCartTotal(){
    return cartService.cartTotal();
  }

}
