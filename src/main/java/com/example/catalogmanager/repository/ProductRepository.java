package com.example.catalogmanager.repository;

import com.example.catalogmanager.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Page<Product> findByName(String name, Pageable pageable);

  Page<Product> findProductsByCategory_Name(String categoryName, Pageable pageable);
}
