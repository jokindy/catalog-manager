package com.example.catalogmanager.service;

import com.example.catalogmanager.dto.category.CategoryCreateDto;
import com.example.catalogmanager.dto.category.CategoryUpdateDto;
import com.example.catalogmanager.dto.category.CategoryViewDto;
import java.util.List;

public interface CategoryService {
  List<CategoryViewDto> getAllCategories(int page, int size);

  CategoryViewDto getCategoryById(Long id);

  CategoryViewDto addCategory(CategoryCreateDto categoryCreateDto);

  CategoryViewDto updateCategory(CategoryUpdateDto categoryUpdateDto);

  void deleteCategory(Long id);
}
