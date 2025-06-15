package com.example.demo.ui;

import com.example.demo.products.model.CartItem;
import com.example.demo.products.service.CartService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route("Cart")
@PageTitle("Cart List")
@RolesAllowed("USER")
public class CartListView extends VerticalLayout {

  private final Grid<CartItem> cartGrid;
  private final CartService cartService;

  public CartListView(CartService cartService) {
    this.cartGrid = new Grid<>(CartItem.class);
    this.cartGrid.setColumns("productName", "sku", "shortDescription", "pricePerUnit", "amount", "totalPrice");
    this.cartService = cartService;
    Text titleText = new Text("Shopping Cart");
    HorizontalLayout title = new HorizontalLayout(titleText);
    Text total = new Text("Total:" + cartService.cartTotal());
    MenuBar menuBar = new MenuBar();
    ComponentEventListener<ClickEvent<MenuItem>> listener = e -> menuBar.getUI().ifPresent(ui -> ui.navigate(e.getSource().getText()));

    menuBar.addItem("Products", listener);
    menuBar.addItem("Cart", listener);
    menuBar.addItem("Logout", listener);
    add(menuBar, title, cartGrid, total);
    listCartProducts();
  }

  private void listCartProducts() {
    cartGrid.setItems(cartService.getAllCartItem());
  }

}
