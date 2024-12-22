package com.example.catalogmanager.repository;

import com.example.catalogmanager.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
