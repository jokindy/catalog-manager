package com.example.catalogmanager.util;

import com.example.catalogmanager.domain.Category;
import com.example.catalogmanager.dto.category.CategoryCreateDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class CategoryTestDataFactory {

  public static Category createCategory() {
    Category category = new Category();

    category.setName("Category 1");
    category.setDescription("Description for category 1");
    category.setLogoUrl("https://category1_logo.jpeg");

    return category;
  }

  public static CategoryCreateDto createCategoryCreateDto() {
    CategoryCreateDto category = new CategoryCreateDto();

    category.setName("Category 1");
    category.setDescription("Description for category 1");
    category.setLogoUrl("https://category1_logo.jpeg");

    return category;
  }

  public static Page<Category> createCategoryPage() {
    Category category1 = new Category();
    category1.setId(1L);
    category1.setName("Category 1");
    category1.setDescription("Description for category 1");
    category1.setLogoUrl("category1_logo.jpeg");

    Category category2 = new Category();
    category2.setId(2L);
    category2.setName("Category 2");
    category2.setDescription("Description for category 2");
    category2.setLogoUrl("category2_logo.jpeg");

    Category category3 = new Category();
    category3.setId(3L);
    category3.setName("Category 3");
    category3.setDescription("Description for category 3");
    category3.setLogoUrl("category3_logo.jpeg");

    return new PageImpl<>(List.of(category1, category2, category3));
  }
}
