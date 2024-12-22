package com.example.catalogmanager.controller;

import com.example.catalogmanager.dto.category.CategoryCreateDto;
import com.example.catalogmanager.dto.category.CategoryUpdateDto;
import com.example.catalogmanager.dto.category.CategoryViewDto;
import com.example.catalogmanager.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

  private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<List<CategoryViewDto>> getAllCategories(
      @PositiveOrZero(message = "Must be greater than or equal to 0")
          @RequestParam(defaultValue = "0")
          int pageNumber,
      @Positive(message = "Must be greater than 0") @RequestParam(defaultValue = "10")
          int pageSize) {
    logger.info(
        "Fetching all categories with page number: {}, page size: {}", pageNumber, pageSize);
    List<CategoryViewDto> categories = categoryService.getAllCategories(pageNumber, pageSize);
    logger.info("Retrieved {} categories", categories.size());
    return ResponseEntity.ok(categories);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryViewDto> getCategoryById(@PathVariable Long id) {
    logger.info("Fetching category with ID: {}", id);
    CategoryViewDto category = categoryService.getCategoryById(id);
    logger.info("Retrieved category: {}", category);
    return ResponseEntity.ok(category);
  }

  @PostMapping
  public ResponseEntity<CategoryViewDto> addCategory(
      @Valid @RequestBody CategoryCreateDto categoryCreateDto) {
    logger.info("Creating new category with details: {}", categoryCreateDto);
    CategoryViewDto createdCategory = categoryService.addCategory(categoryCreateDto);
    logger.info("Created category: {}", createdCategory);
    return ResponseEntity.ok(createdCategory);
  }

  @PutMapping
  public ResponseEntity<CategoryViewDto> updateCategory(
      @Valid @RequestBody CategoryUpdateDto categoryUpdateDto) {
    logger.info("Updating category with details: {}", categoryUpdateDto);
    CategoryViewDto updatedCategory = categoryService.updateCategory(categoryUpdateDto);
    logger.info("Updated category: {}", updatedCategory);
    return ResponseEntity.ok(updatedCategory);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    logger.info("Deleting category with ID: {}", id);
    categoryService.deleteCategory(id);
    logger.info("Deleted category with ID: {}", id);
    return ResponseEntity.noContent().build();
  }
}
