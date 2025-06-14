package com.example.demo.ui;

import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route(value = "login", autoLayout = false)
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends Main implements BeforeEnterObserver {

  private final LoginForm login;

  public LoginView() {
    addClassNames(LumoUtility.Display.FLEX, LumoUtility.JustifyContent.CENTER,
            LumoUtility.AlignItems.CENTER);
    setSizeFull();
    login = new LoginForm();
    login.setAction("login");
    add(login);
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    if (event.getLocation()
            .getQueryParameters()
            .getParameters()
            .containsKey("error")) {
      login.setError(true);
    }
  }
}