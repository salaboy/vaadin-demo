package com.example.demo.ui;


import com.example.demo.products.model.CartServiceItem;
import com.example.demo.products.service.CartService;
import com.example.demo.products.service.SessionsService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route("sessions")
@PageTitle("Sessions List")
@RolesAllowed("ADMIN")
public class SessionListView extends VerticalLayout {


  private final Grid<HttpSession> sessionsGrid;
  private final Grid<CartServiceItem> cartServicesGrid;
  private final SessionsService sessionsService;

  public SessionListView(SessionsService sessionsService) {
    this.sessionsGrid = new Grid<>(HttpSession.class);
    this.cartServicesGrid = new Grid<>(CartServiceItem.class);
    Text titleText = new Text("Sessions List");
    MenuBar menuBar = new MenuBar();
    ComponentEventListener<ClickEvent<MenuItem>> listener = e -> menuBar.getUI().ifPresent(ui -> ui.navigate(e.getSource().getText()));
    menuBar.addItem("Logout", listener);
    cartServicesGrid.setColumns("sessionId", "items");
    add(menuBar, titleText, cartServicesGrid, sessionsGrid);
    this.sessionsService = sessionsService;

    listHttpSessions();
  }

  private void listHttpSessions() {
    this.sessionsGrid.setItems(this.sessionsService.getAllSessions());
    this.cartServicesGrid.setItems(this.sessionsService.getCarts());
  }

}
