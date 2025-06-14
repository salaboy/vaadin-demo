package com.example.demo.products.model;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@RedisHash("Product")
public class Product implements Serializable {
  private String id;
  private String sku;
  private String name;
  private String description;
  private BigDecimal price;

  public Product() {
    this.id = UUID.randomUUID().toString();
  }

  public Product(String sku, String name, String description, BigDecimal price) {
    this();
    this.sku = sku;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Product{" +
            "id='" + id + '\'' +
            ", sku='" + sku + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", price=" + price +
            '}';
  }
}
