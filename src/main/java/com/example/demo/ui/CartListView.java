package com.example.demo.ui;

import com.example.demo.products.model.Product;
import com.example.demo.products.service.CartService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.util.StringUtils;

@Route("cart")
@PageTitle("Cart List")
@RolesAllowed("USER")
public class CartListView extends VerticalLayout {

  private final Grid<Product> cartGrid;
  private final CartService cartService;

  public CartListView(CartService cartService) {
    this.cartGrid = new Grid<>(Product.class);
    this.cartService = cartService;
    Text titleText = new Text("Shopping Cart");
    HorizontalLayout title = new HorizontalLayout(titleText);
    add(titleText, cartGrid);
    listCartProducts();
  }

  private void listCartProducts() {
    cartGrid.setItems(cartService.getAllCartProducts());
  }

}
