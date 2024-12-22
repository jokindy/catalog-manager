package com.example.catalogmanager.repository;

import static com.example.catalogmanager.util.CategoryTestDataFactory.createCategory;
import static com.example.catalogmanager.util.ProductTestDataFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.catalogmanager.domain.Category;
import com.example.catalogmanager.domain.Product;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProductRepositoryUnitTest {

  @Autowired private ProductRepository productRepository;
  @Autowired private CategoryRepository categoryRepository;

  private Category testCategory;
  private Product testProduct;

  @BeforeEach
  void setUp() {
    testCategory = createCategory();

    categoryRepository.save(testCategory);

    testProduct = createProduct();
    testProduct.setCategory(testCategory);

    productRepository.save(testProduct);
  }

  @Test
  void shouldSaveProduct() {
    Product newProduct = new Product();
    newProduct.setName("Laptop");
    newProduct.setDescription("High-performance laptop");
    newProduct.setPrice(BigDecimal.valueOf(1299.99));
    newProduct.setStockQuantity(20);
    newProduct.setCategory(testCategory);

    Product savedProduct = productRepository.save(newProduct);

    assertThat(savedProduct.getId()).isNotNull();
    assertThat(savedProduct.getName()).isEqualTo("Laptop");
  }

  @Test
  void shouldFindProductById() {
    Optional<Product> foundProduct = productRepository.findById(testProduct.getId());

    assertThat(foundProduct).isPresent();
    assertThat(foundProduct.get().getName()).isEqualTo("Test product name");
  }

  @Test
  void shouldFindAllProducts() {
    Page<Product> products = productRepository.findAll(PageRequest.of(0, 10));

    assertThat(products.getTotalElements()).isEqualTo(1);
    assertThat(products.getContent().getFirst().getName()).isEqualTo("Test product name");
  }

  @Test
  void shouldDeleteProduct() {
    productRepository.delete(testProduct);

    Optional<Product> deletedProduct = productRepository.findById(testProduct.getId());
    assertThat(deletedProduct).isEmpty();
  }

  @Test
  void shouldUpdateProduct() {
    testProduct.setPrice(BigDecimal.valueOf(799.99));
    Product updatedProduct = productRepository.save(testProduct);

    assertThat(updatedProduct.getPrice()).isEqualTo(BigDecimal.valueOf(799.99));
  }

  @Test
  void shouldFindProductsByName() {
    Page<Product> products =
        productRepository.findByName("Test product name", PageRequest.of(0, 10));

    assertThat(products.getTotalElements()).isEqualTo(1);
    assertThat(products.getContent().getFirst().getDescription())
        .isEqualTo("Test product description");
  }

  @Test
  void shouldFindProductsByCategoryName() {
    Page<Product> products =
        productRepository.findProductsByCategory_Name("Category 1", PageRequest.of(0, 10));

    assertThat(products.getTotalElements()).isEqualTo(1);
    assertThat(products.getContent().getFirst().getName()).isEqualTo("Test product name");
  }
}
