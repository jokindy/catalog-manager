package com.example.catalogmanager.mapper;

import static com.example.catalogmanager.util.CategoryTestDataFactory.createCategory;
import static com.example.catalogmanager.util.CategoryTestDataFactory.createCategoryCreateDto;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.catalogmanager.domain.Category;
import com.example.catalogmanager.dto.category.CategoryCreateDto;
import com.example.catalogmanager.dto.category.CategoryViewDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CategoryMapperUnitTest {

  private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

  @Test
  void toCategoryViewDto() {
    Category category = createCategory();

    CategoryViewDto categoryViewDto = categoryMapper.toDto(category);

    assertThat(categoryViewDto.getName()).isEqualTo("Category 1");
    assertThat(categoryViewDto.getDescription()).isEqualTo("Description for category 1");
    assertThat(categoryViewDto.getLogoUrl()).isEqualTo("https://category1_logo.jpeg");
  }

  @Test
  void toCategory() {
    CategoryCreateDto categoryCreateDto = createCategoryCreateDto();

    Category category = categoryMapper.toEntity(categoryCreateDto);

    assertThat(category.getName()).isEqualTo("Category 1");
    assertThat(category.getDescription()).isEqualTo("Description for category 1");
    assertThat(category.getLogoUrl()).isEqualTo("https://category1_logo.jpeg");
  }
}
