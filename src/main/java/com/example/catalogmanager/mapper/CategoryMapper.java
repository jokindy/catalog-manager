package com.example.catalogmanager.mapper;

import com.example.catalogmanager.domain.Category;
import com.example.catalogmanager.dto.category.CategoryCreateDto;
import com.example.catalogmanager.dto.category.CategoryViewDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

  public abstract CategoryViewDto toDto(Category category);

  public abstract Category toEntity(CategoryCreateDto categoryCreateDto);
}
