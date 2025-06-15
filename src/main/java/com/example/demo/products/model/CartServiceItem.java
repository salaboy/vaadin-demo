package com.example.demo.products.model;

import java.io.Serializable;

public class CartServiceItem implements Serializable {
  private String sessionId;
  private Integer items;

  public CartServiceItem() {
  }

  public CartServiceItem(String sessionId, Integer items) {
    this.sessionId = sessionId;
    this.items = items;
  }

  public String getSessionId() {
    return sessionId;
  }

  public Integer getItems() {
    return items;
  }
}
