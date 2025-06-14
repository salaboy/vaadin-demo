package com.example.demo.products.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;

@Component
@ApplicationScope
public class SessionsService {
  private final List<HttpSession> sessions = new ArrayList<>();

  public void registerHttpSession(HttpSession session) {
    this.sessions.add(session);
  }

  public List<HttpSession> getAllSessions() {
    return this.sessions;
  }


  public void removeHttpSession(HttpSession session) {
    this.sessions.remove(session);
  }

}
