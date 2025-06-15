package com.example.demo.ui;

import com.example.demo.products.model.Product;
import com.example.demo.products.repository.ProductRepository;
import com.example.demo.products.service.CartService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Route("")
@RouteAlias("Products")
@PageTitle("Product List")
@RolesAllowed("USER")
public class ProductsListView extends VerticalLayout{

  private final ProductRepository productRepository;
  private final Grid<Product> productGrid;
  private final ProductEditor editor;
  private final TextField filter;
  private final Button addNewBtn;

  public ProductsListView(ProductRepository productRepository , ProductEditor editor) {

    this.productRepository = productRepository;
    this.productGrid = new Grid<>(Product.class);
    this.editor = editor;
    this.filter = new TextField();
    this.addNewBtn = new Button("New product", VaadinIcon.PLUS.create());

    MenuBar menuBar = new MenuBar();
    ComponentEventListener<ClickEvent<MenuItem>> listener = e -> menuBar.getUI().ifPresent(ui -> ui.navigate(e.getSource().getText()));

    menuBar.addItem("Products", listener);
    menuBar.addItem("Cart", listener);
    menuBar.addItem("Logout", listener);

    // build layout
    Text titleText = new Text("Products List");
    HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
    add(menuBar, titleText, actions, productGrid, editor);

    productGrid.setHeight("400px");
    productGrid.setColumns("sku", "name", "description","price");
    productGrid.getColumnByKey("sku").setWidth("150px").setFlexGrow(0);

    filter.setPlaceholder("Filter by description");

    filter.setValueChangeMode(ValueChangeMode.LAZY);
    filter.addValueChangeListener(e -> listProducts(e.getValue()));

    // Connect selected Customer to editor or hide if none is selected
    productGrid.asSingleSelect().addValueChangeListener(e -> {
      editor.editProduct(e.getValue());
    });

    // Instantiate and edit new Product the new button is clicked
    addNewBtn.addClickListener(e -> editor.editProduct(new Product("", "", "", new BigDecimal(0))));

    // Listen changes made by the editor, refresh data from backend
    editor.setChangeHandler(() -> {
      editor.setVisible(false);
      listProducts(filter.getValue());
    });

    listProducts(null);
  }

  private void listProducts(String filterText) {
    if (StringUtils.hasText(filterText)) {
      productGrid.setItems(productRepository.findByDescriptionIgnoreCase(filterText));
    } else {
      productGrid.setItems(productRepository.findAll());
    }
  }
}
