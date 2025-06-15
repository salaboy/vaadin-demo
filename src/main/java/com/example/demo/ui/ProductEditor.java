package com.example.demo.ui;

import com.example.demo.products.model.Product;
import com.example.demo.products.repository.ProductRepository;
import com.example.demo.products.service.CartService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class ProductEditor extends VerticalLayout implements KeyNotifier {

  private final ProductRepository repository;
  private final CartService cartService;

  /**
   * The currently edited customer
   */
  private Product product;

  /* Fields to edit properties in Customer entity */
  TextField sku = new TextField("SKU");
  TextField name = new TextField("Product Name");
  TextField description = new TextField("Product Description");
  TextField price = new TextField("Product price");

  /* Action buttons */
  Button save = new Button("Save", VaadinIcon.CHECK.create());
  Button cancel = new Button("Cancel");
  Button delete = new Button("Delete", VaadinIcon.TRASH.create());
  Button addToCart = new Button("Add To Cart", VaadinIcon.PLUS.create());
  HorizontalLayout actions = new HorizontalLayout(save, cancel, delete, addToCart);

  Binder<Product> binder = new Binder<>(Product.class);
  private ChangeHandler changeHandler;

  @Autowired
  public ProductEditor(ProductRepository repository, CartService cartService) {
    this.repository = repository;
    this.cartService = cartService;

    add(sku, name, description, price, actions);

    // bind using naming convention
    binder.bindInstanceFields(this);

    // Configure and style components
    setSpacing(true);

    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

    addKeyPressListener(Key.ENTER, e -> save());

    // wire action buttons to save, delete and reset
    save.addClickListener(e -> save());
    delete.addClickListener(e -> delete());
    cancel.addClickListener(e -> editProduct(product));
    addToCart.addClickListener(e -> addProductToCart(product));
    setVisible(false);
  }

  void delete() {
    repository.delete(product);
    changeHandler.onChange();
  }

  void save() {
    repository.save(product);
    changeHandler.onChange();
  }

  public interface ChangeHandler {
    void onChange();
  }

  public final void addProductToCart(Product p) {
    cartService.addProductToCart(p);
    Notification
            .show("Product: " + product.getName() + "added to cart!");
  }

  public final void editProduct(Product p) {
    if (p == null) {
      setVisible(false);
      return;
    }
    // Do the check with SKU as the id is created on construction
    final boolean persisted = !p.getSku().isEmpty();
    if (persisted) {
      // Find fresh entity for editing
      // In a more complex app, you might want to load
      // the entity/DTO with lazy loaded relations for editing
      product = repository.findById(p.getId()).get();
    }
    else {
      product = p;
    }
    cancel.setVisible(persisted);

    // Bind customer properties to similarly named fields
    // Could also use annotation or "manual binding" or programmatically
    // moving values from fields to entities before saving
    binder.setBean(product);

    setVisible(true);

    // Focus first sku initially
    sku.focus();
  }

  public void setChangeHandler(ChangeHandler h) {
    // ChangeHandler is notified when either save or delete
    // is clicked
    changeHandler = h;
  }

}
