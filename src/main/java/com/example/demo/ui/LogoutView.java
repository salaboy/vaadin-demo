package com.example.demo.ui;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;

@Route("Logout")
@PermitAll
public class LogoutView extends VerticalLayout {

  public LogoutView(AuthenticationContext authenticationContext) {
    add(new Button("Logout", event -> authenticationContext.logout()));
  }
}
