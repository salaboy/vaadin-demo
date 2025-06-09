package com.example.demo.ui;

import com.example.demo.products.model.Product;
import com.example.demo.products.service.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class ProductCatalogUI extends VerticalLayout{

  @Autowired
  private ProductService productService;

  public ProductCatalogUI() {
    var ui = UI.getCurrent();


    var productGrid = new Grid<>(Product.class);
    productGrid.setColumns("id", "sku", "name", "price");

    Iterable<Product> products = productService.getProducts();
    //productGrid.setItems(products);
    add(productGrid);

  }
}
