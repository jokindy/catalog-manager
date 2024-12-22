package com.example.catalogmanager.controller;

import static com.example.catalogmanager.util.CategoryTestDataFactory.createCategory;
import static com.example.catalogmanager.util.ProductTestDataFactory.createProductCreateDto;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalogmanager.domain.Category;
import com.example.catalogmanager.dto.product.ProductCreateDto;
import com.example.catalogmanager.dto.product.ProductUpdateDto;
import com.example.catalogmanager.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductAdminControllerTest {

  @Autowired private ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;
  @Autowired private CategoryRepository categoryRepository;

  private Category category;

  @BeforeEach
  public void beforeEach() {
    category = createCategory();

    categoryRepository.save(category);
  }

  @Test
  @Order(1)
  void testCreateProduct_Success() throws Exception {
    ProductCreateDto productCreateDto = createProductCreateDto();
    productCreateDto.setCategoryId(category.getId());

    mockMvc
        .perform(
            post("/api/v1/products")
                .content(objectMapper.writeValueAsString(productCreateDto))
                .contentType(ContentType.APPLICATION_JSON.toString()))
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
  @Order(2)
  void testCreateProduct_InvalidDto() throws Exception {
    ProductCreateDto productCreateDto = new ProductCreateDto();

    productCreateDto.setName("T");
    productCreateDto.setDescription(null);
    productCreateDto.setPrice(BigDecimal.valueOf(-9.99));
    productCreateDto.setStockQuantity(-47);
    productCreateDto.setCategoryId(null);

    mockMvc
        .perform(
            post("/api/v1/products")
                .content(objectMapper.writeValueAsString(productCreateDto))
                .contentType(ContentType.APPLICATION_JSON.toString()))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.timestamp", notNullValue()),
            jsonPath("$.traceId", notNullValue()),
            jsonPath("$.faults", hasSize(5)),
            jsonPath(
                "$.faults[*].message",
                containsInAnyOrder(
                    "Invalid parameter: name",
                    "Invalid parameter: description",
                    "Invalid parameter: price",
                    "Invalid parameter: stockQuantity",
                    "Invalid parameter: categoryId")),
            jsonPath(
                "$.faults[*].reason",
                containsInAnyOrder(
                    "Must be between 3 and 85 characters",
                    "Must not me null",
                    "Must be positive",
                    "Must be positive",
                    "Must not me null")));
  }

  @Test
  @Order(3)
  void testUpdateCategory_InvalidDto() throws Exception {
    ProductUpdateDto productUpdateDto = new ProductUpdateDto();

    productUpdateDto.setId(1L);
    productUpdateDto.setName("T");
    productUpdateDto.setDescription(null);
    productUpdateDto.setPrice(BigDecimal.valueOf(-9.99));
    productUpdateDto.setStockQuantity(-47);
    productUpdateDto.setCategoryId(null);

    mockMvc
        .perform(
            put("/api/v1/products")
                .content(objectMapper.writeValueAsString(productUpdateDto))
                .contentType(ContentType.APPLICATION_JSON.toString()))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.timestamp", notNullValue()),
            jsonPath("$.traceId", notNullValue()),
            jsonPath("$.faults", hasSize(4)),
            jsonPath(
                "$.faults[*].message",
                containsInAnyOrder(
                    "Invalid parameter: name",
                    "Invalid parameter: description",
                    "Invalid parameter: stockQuantity",
                    "Invalid parameter: categoryId")),
            jsonPath(
                "$.faults[*].reason",
                containsInAnyOrder(
                    "Must be between 3 and 85 characters",
                    "Must not me null",
                    "Must be positive",
                    "Must not me null")));
  }

  @Test
  @Order(4)
  void testUpdateCategory_NonExistingId() throws Exception {
    ProductUpdateDto productUpdateDto = new ProductUpdateDto();

    productUpdateDto.setId(222222L);
    productUpdateDto.setName("Test product name");
    productUpdateDto.setDescription("Test product description");
    productUpdateDto.setPrice(BigDecimal.valueOf(9.99));
    productUpdateDto.setStockQuantity(43);
    productUpdateDto.setCategoryId(1L);

    mockMvc
        .perform(
            put("/api/v1/products")
                .content(objectMapper.writeValueAsString(productUpdateDto))
                .contentType(ContentType.APPLICATION_JSON.toString()))
        .andExpectAll(
            status().isNotFound(),
            jsonPath("$.timestamp", notNullValue()),
            jsonPath("$.traceId", notNullValue()),
            jsonPath("$.faults", hasSize(1)),
            jsonPath("$.faults[0].message", is("Entity not found")),
            jsonPath("$.faults[0].reason", is("Wrong id")));
  }

  @Test
  @Order(5)
  void testUpdateCategory_Success() throws Exception {
    Long categoryId = prepareCategoryForUpdate();

    ProductUpdateDto productUpdateDto = new ProductUpdateDto();

    productUpdateDto.setId(1L);
    productUpdateDto.setName("New product name");
    productUpdateDto.setDescription("New product description");
    productUpdateDto.setPrice(BigDecimal.valueOf(7.77));
    productUpdateDto.setStockQuantity(22);
    productUpdateDto.setCategoryId(categoryId);

    mockMvc
        .perform(
            put("/api/v1/products")
                .content(objectMapper.writeValueAsString(productUpdateDto))
                .contentType(ContentType.APPLICATION_JSON.toString()))
        .andExpectAll(
            status().isOk(),
            jsonPath("$.name", is("New product name")),
            jsonPath("$.description", is("New product description")),
            jsonPath("$.price", is(7.77)),
            jsonPath("$.stockQuantity", is(22)),
            jsonPath("$.category.name", is("New category")),
            jsonPath("$.category.description", is("New description")));
  }

  private Long prepareCategoryForUpdate() {
    Category updatedCategory = new Category();
    updatedCategory.setName("New category");
    updatedCategory.setDescription("New description");
    updatedCategory.setLogoUrl("https://google.com");

    return categoryRepository.save(updatedCategory).getId();
  }
}
