package com.example.demo.config;

import com.example.demo.products.service.SessionsService;
import jakarta.servlet.http.HttpSessionEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class MyHttpSessionListener implements jakarta.servlet.http.HttpSessionListener {

  private final AtomicInteger activeSessions;
  private final SessionsService sessionsService;

  public MyHttpSessionListener(SessionsService sessionsService) {
    super();
    this.sessionsService = sessionsService;
    activeSessions = new AtomicInteger();
  }

  public int getTotalActiveSession() {
    return activeSessions.get();
  }

  public void sessionCreated(final HttpSessionEvent event) {
    activeSessions.incrementAndGet();
    sessionsService.registerHttpSession(event.getSession());
  }
  public void sessionDestroyed(final HttpSessionEvent event) {
    activeSessions.decrementAndGet();
    sessionsService.removeHttpSession(event.getSession());
  }
}
