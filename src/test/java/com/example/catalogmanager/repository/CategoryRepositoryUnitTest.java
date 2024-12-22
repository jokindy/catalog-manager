package com.example.catalogmanager.repository;

import static com.example.catalogmanager.util.CategoryTestDataFactory.createCategory;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.catalogmanager.domain.Category;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CategoryRepositoryUnitTest {

  @Autowired private CategoryRepository categoryRepository;
  private Category testCategory;

  @BeforeEach
  void setUp() {
    testCategory = createCategory();

    categoryRepository.save(testCategory);
  }

  @Test
  void shouldSaveCategory() {
    Category newCategory = new Category();
    newCategory.setName("Books");
    newCategory.setDescription("Category for books");
    newCategory.setLogoUrl("https://example.com/book_logo.png");

    Category savedCategory = categoryRepository.save(newCategory);

    assertThat(savedCategory.getId()).isNotNull();
    assertThat(savedCategory.getName()).isEqualTo("Books");
  }

  @Test
  void shouldFindCategoryById() {
    Optional<Category> foundCategory = categoryRepository.findById(testCategory.getId());

    assertThat(foundCategory).isPresent();
    assertThat(foundCategory.get().getName()).isEqualTo("Category 1");
  }

  @Test
  void shouldFindAllCategories() {
    List<Category> categories = categoryRepository.findAll();

    assertThat(categories).hasSize(1);
    assertThat(categories.getFirst().getName()).isEqualTo("Category 1");
  }

  @Test
  void shouldDeleteCategory() {
    categoryRepository.delete(testCategory);

    Optional<Category> deletedCategory = categoryRepository.findById(testCategory.getId());
    assertThat(deletedCategory).isEmpty();
  }

  @Test
  void shouldUpdateCategory() {
    testCategory.setName("Updated name");
    Category updatedCategory = categoryRepository.save(testCategory);

    assertThat(updatedCategory.getName()).isEqualTo("Updated name");
  }
}
