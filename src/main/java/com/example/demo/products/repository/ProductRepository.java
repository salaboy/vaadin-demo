package com.example.demo.products.repository;

import com.example.demo.products.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, String> {
  List<Product> findByDescriptionIgnoreCase(String description);

}
