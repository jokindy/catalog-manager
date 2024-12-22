package com.example.catalogmanager.controller;

import static com.example.catalogmanager.util.CategoryTestDataFactory.createCategory;
import static com.example.catalogmanager.util.ProductTestDataFactory.createProduct;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalogmanager.domain.Category;
import com.example.catalogmanager.domain.Product;
import com.example.catalogmanager.repository.CategoryRepository;
import com.example.catalogmanager.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ProductUserControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ProductRepository productRepository;
  @Autowired private CategoryRepository categoryRepository;
  private Product product;

  @BeforeEach
  public void beforeEach() {
    product = createProduct();
    Category category = createCategory();

    categoryRepository.save(category);

    product.setCategory(category);

    productRepository.save(product);
  }

  @AfterEach
  public void afterEach() {
    productRepository.deleteAll();
    categoryRepository.deleteAll();
  }

  @Test
  void testGetProductById_Success() throws Exception {
    mockMvc
        .perform(get("/api/v1/products/{id}", product.getId()))
        .andExpectAll(
            status().isOk(),
            jsonPath("$.name", is("Test product name")),
            jsonPath("$.description", is("Test product description")),
            jsonPath("$.price", is(9.99)),
            jsonPath("$.stockQuantity", is(30)),
            jsonPath("$.category.name", is("Category 1")),
            jsonPath("$.category.description", is("Description for category 1")));
  }

  @Test
  void testGetProductByNonExistentId_404() throws Exception {
    mockMvc
        .perform(get("/api/v1/products/100000"))
        .andExpectAll(
            status().isNotFound(),
            jsonPath("$.timestamp", notNullValue()),
            jsonPath("$.traceId", notNullValue()),
            jsonPath("$.faults", hasSize(1)),
            jsonPath("$.faults[0].message", is("Entity not found")),
            jsonPath("$.faults[0].reason", is("Wrong id")));
  }

  @Test
  void testGetProductByInvalidId_400() throws Exception {
    mockMvc
        .perform(get("/api/v1/products/AA"))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.timestamp", notNullValue()),
            jsonPath("$.traceId", notNullValue()),
            jsonPath("$.faults", hasSize(1)),
            jsonPath("$.faults[0].message", is("Invalid parameter: id")));
  }

  @Test
  void testGetAllProducts_Success() throws Exception {
    mockMvc
        .perform(get("/api/v1/products"))
        .andExpectAll(status().isOk(), jsonPath("$", hasSize(1)))
        .andExpectAll(
            jsonPath("$[0].name", is("Test product name")),
            jsonPath("$[0].description", is("Test product description")),
            jsonPath("$[0].price", is(9.99)),
            jsonPath("$[0].stockQuantity", is(30)),
            jsonPath("$[0].category.name", is("Category 1")),
            jsonPath("$[0].category.description", is("Description for category 1")));
  }

  @Test
  void testGetAllProducts_InvalidRequestParams() throws Exception {
    mockMvc
        .perform(get("/api/v1/products").param("pageSize", "-2").param("pageNumber", "-9"))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.timestamp", notNullValue()),
            jsonPath("$.traceId", notNullValue()),
            jsonPath("$.faults", hasSize(2)),
            jsonPath(
                "$.faults[*].message",
                containsInAnyOrder("Invalid parameter: pageSize", "Invalid parameter: pageNumber")),
            jsonPath(
                "$.faults[*].reason",
                containsInAnyOrder(
                    "Must be greater than or equal to 0", "Must be greater than 0")));
  }

  @Test
  void testSearchProductsByName_Success() throws Exception {
    mockMvc
        .perform(get("/api/v1/products/search").param("name", "Test product name"))
        .andExpectAll(status().isOk(), jsonPath("$", hasSize(1)))
        .andExpectAll(
            jsonPath("$[0].name", is("Test product name")),
            jsonPath("$[0].description", is("Test product description")),
            jsonPath("$[0].price", is(9.99)),
            jsonPath("$[0].stockQuantity", is(30)),
            jsonPath("$[0].category.name", is("Category 1")),
            jsonPath("$[0].category.description", is("Description for category 1")));
  }

  @Test
  void testSearchProductsByName_InvalidRequestParam() throws Exception {
    mockMvc
        .perform(get("/api/v1/products/search").param("name", ""))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.timestamp", notNullValue()),
            jsonPath("$.traceId", notNullValue()),
            jsonPath("$.faults", hasSize(2)),
            jsonPath(
                "$.faults[*].message",
                containsInAnyOrder("Invalid parameter: name", "Invalid parameter: name")),
            jsonPath(
                "$.faults[*].reason",
                containsInAnyOrder("Must be present", "Must be between 3 and 85 characters")));
  }

  @Test
  void testSearchProductsByCategoryName_InvalidRequestParam() throws Exception {
    mockMvc
        .perform(get("/api/v1/products/category"))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.timestamp", notNullValue()),
            jsonPath("$.traceId", notNullValue()),
            jsonPath("$.faults", hasSize(1)),
            jsonPath("$.faults[0].message", is("Invalid parameter: categoryName")),
            jsonPath(
                "$.faults[0].reason",
                is(
                    "Required request parameter 'categoryName' for method parameter type String is not present")));
  }
}
