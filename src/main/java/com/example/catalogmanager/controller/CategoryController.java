package com.example.catalogmanager.controller;

import com.example.catalogmanager.dto.category.CategoryCreateDto;
import com.example.catalogmanager.dto.category.CategoryUpdateDto;
import com.example.catalogmanager.dto.category.CategoryViewDto;
import com.example.catalogmanager.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
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
    return ResponseEntity.ok(categoryService.getAllCategories(pageNumber, pageSize));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryViewDto> getCategoryById(@PathVariable Long id) {
    return ResponseEntity.ok(categoryService.getCategoryById(id));
  }

  @PostMapping
  public ResponseEntity<CategoryViewDto> addCategory(
      @Valid @RequestBody CategoryCreateDto categoryCreateDto) {
    return ResponseEntity.ok(categoryService.addCategory(categoryCreateDto));
  }

  @PutMapping
  public ResponseEntity<CategoryViewDto> updateCategory(
      @Valid @RequestBody CategoryUpdateDto categoryUpdateDto) {
    return ResponseEntity.ok(categoryService.updateCategory(categoryUpdateDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }
}
