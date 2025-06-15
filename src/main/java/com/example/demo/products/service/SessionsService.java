package com.example.demo.products.service;

import com.example.demo.products.model.CartServiceItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@Component
@ApplicationScope
public class SessionsService {

  @Autowired
  private ApplicationContext applicationContext;

  private final List<HttpSession> sessions = new ArrayList<>();
  private final List<CartServiceItem> cartServiceItems = new ArrayList<>();

  public void registerHttpSession(HttpSession session) {
    this.sessions.add(session);
  }

  public List<HttpSession> getAllSessions() {
    return this.sessions;
  }

  public List<CartServiceItem> getCarts() {
    for (HttpSession s : sessions) {
      Object attribute = s.getAttribute("scopedTarget.cartService");
      boolean isCartItemPresent = cartServiceItems.stream()
              .anyMatch(cartItem -> cartItem.getSessionId().equals(s.getId()));
      if (attribute instanceof CartService cartService) {
        if (!isCartItemPresent) {
          this.cartServiceItems.add(new CartServiceItem(s.getId(), cartService.getItemsCount()));
        }
      }
    }
    return cartServiceItems;
  }

  public void removeHttpSession(HttpSession session) {
    this.sessions.remove(session);
  }


}
