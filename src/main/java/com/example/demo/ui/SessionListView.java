package com.example.demo.ui;


import com.example.demo.products.service.SessionsService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpSession;

@Route("sessions")
@PageTitle("Sessions List")
@RolesAllowed("ADMIN")
public class SessionListView extends VerticalLayout {


  private final Grid<HttpSession> sessionsGrid;
  private final SessionsService sessionsService;

  public SessionListView(SessionsService sessionsService) {
    this.sessionsGrid = new Grid<>(HttpSession.class);
    Text titleText = new Text("Sessions List");
    add(titleText, sessionsGrid);
    this.sessionsService = sessionsService;
    listHttpSessions();
  }

  private void listHttpSessions() {
    this.sessionsGrid.setItems(this.sessionsService.getAllSessions());
  }

}
