package com.example.demo.ui;

import com.example.demo.products.model.CartItem;
import com.example.demo.products.service.CartService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route("cart")
@PageTitle("Cart List")
@RolesAllowed("USER")
public class CartListView extends VerticalLayout {

  private final Grid<CartItem> cartGrid;
  private final CartService cartService;

  public CartListView(CartService cartService) {
    this.cartGrid = new Grid<>(CartItem.class);
    this.cartGrid.setColumns("productName", "sku", "shortDescription", "price", "amount");
    this.cartService = cartService;
    Text titleText = new Text("Shopping Cart");
    HorizontalLayout title = new HorizontalLayout(titleText);
    Text total = new Text("Total:" + cartService.cartTotal());
    add(title, cartGrid, total);
    listCartProducts();
  }

  private void listCartProducts() {
    cartGrid.setItems(cartService.getAllCartItem());
  }

}
