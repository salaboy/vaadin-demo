package com.example.demo.products.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem implements Serializable {
  private final Product product;
  private final String sku;
  private final String productName;
  private final String shortDescription;
  private final BigDecimal pricePerUnit;
  private final BigDecimal totalPrice;
  private final Integer amount;


  public CartItem(Product product, Integer amount) {
    this.product = product;
    this.amount = amount;
    this.sku = product.getSku();
    this.productName = product.getName();
    this.pricePerUnit = product.getPrice();
    this.totalPrice = product.getPrice().multiply(new BigDecimal(amount));
    this.shortDescription = product.getDescription().substring(0,5); // extremely short

  }

  public String getSku() {
    return sku;
  }

  public String getProductName() {
    return productName;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public BigDecimal getPricePerUnit() {
    return pricePerUnit;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public Integer getAmount() {
    return amount;
  }

  public Product getProduct() {
    return product;
  }

  @Override
  public String toString() {
    return "CartItem{" +
            "product=" + product +
            ", sku='" + sku + '\'' +
            ", productName='" + productName + '\'' +
            ", shortDescription='" + shortDescription + '\'' +
            ", pricePerUnit=" + pricePerUnit +
            ", totalPrice=" + totalPrice +
            ", amount=" + amount +
            '}';
  }
}
