package com.example.catalogmanager.service.impl;

import com.example.catalogmanager.domain.Category;
import com.example.catalogmanager.dto.category.CategoryCreateDto;
import com.example.catalogmanager.dto.category.CategoryUpdateDto;
import com.example.catalogmanager.dto.category.CategoryViewDto;
import com.example.catalogmanager.error.exception.NotFoundException;
import com.example.catalogmanager.mapper.CategoryMapper;
import com.example.catalogmanager.repository.CategoryRepository;
import com.example.catalogmanager.service.CategoryService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
    this.categoryRepository = categoryRepository;
    this.categoryMapper = categoryMapper;
  }

  @Override
  public List<CategoryViewDto> getAllCategories(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    List<Category> categories = categoryRepository.findAll(pageable).getContent();

    return categories.stream().map(categoryMapper::toDto).toList();
  }

  @Override
  public CategoryViewDto getCategoryById(Long id) {
    Category category = categoryRepository.findById(id).orElseThrow(NotFoundException::new);

    return categoryMapper.toDto(category);
  }

  @Override
  @Transactional
  public CategoryViewDto addCategory(CategoryCreateDto categoryCreateDto) {
    Category category = categoryMapper.toEntity(categoryCreateDto);
    category = categoryRepository.save(category);

    return categoryMapper.toDto(category);
  }

  @Override
  @Transactional
  public CategoryViewDto updateCategory(CategoryUpdateDto categoryUpdateDto) {
    Category existingCategory =
        categoryRepository.findById(categoryUpdateDto.getId()).orElseThrow(NotFoundException::new);

    existingCategory.setName(categoryUpdateDto.getName());
    existingCategory.setDescription(categoryUpdateDto.getDescription());
    existingCategory.setLogoUrl(categoryUpdateDto.getLogoUrl());

    Category updatedCategory = categoryRepository.save(existingCategory);

    return categoryMapper.toDto(updatedCategory);
  }

  @Override
  public void deleteCategory(Long id) {
    Category category = categoryRepository.findById(id).orElseThrow(NotFoundException::new);
    categoryRepository.delete(category);
  }
}
