package com.example.catalogmanager.controller;

import static com.example.catalogmanager.util.CategoryTestDataFactory.createCategory;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalogmanager.domain.Category;
import com.example.catalogmanager.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CategoryUserControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private CategoryRepository categoryRepository;

  private Category category;

  @BeforeEach
  public void beforeEach() {
    category = createCategory();
    categoryRepository.save(category);
  }

  @AfterEach
  public void afterEach() {
    categoryRepository.deleteAll();
  }

  @Test
  void testGetCategoryById_Success() throws Exception {
    mockMvc
        .perform(get("/api/v1/categories/{id}", category.getId()))
        .andExpectAll(
            status().isOk(),
            jsonPath("$.name").value("Category 1"),
            jsonPath("$.description").value("Description for category 1"),
            jsonPath("$.logoUrl").value("https://category1_logo.jpeg"));
  }

  @Test
  void testGetCategoryByNonExistentId_404() throws Exception {
    mockMvc
        .perform(get("/api/v1/categories/1414141"))
        .andExpectAll(
            status().isNotFound(),
            jsonPath("$.timestamp", notNullValue()),
            jsonPath("$.traceId", notNullValue()),
            jsonPath("$.faults", hasSize(1)),
            jsonPath("$.faults[0].message", is("Entity not found")),
            jsonPath("$.faults[0].reason", is("Wrong id")));
  }

  @Test
  void testGetCategoryByInvalidId_400() throws Exception {
    mockMvc
        .perform(get("/api/v1/categories/AA"))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.timestamp", notNullValue()),
            jsonPath("$.traceId", notNullValue()),
            jsonPath("$.faults", hasSize(1)),
            jsonPath("$.faults[0].message", is("Invalid parameter: id")));
  }

  @Test
  void testGetAllCategories_Success() throws Exception {
    mockMvc
        .perform(get("/api/v1/categories"))
        .andExpectAll(
            status().isOk(),
            jsonPath("$", hasSize(1)),
            jsonPath("$[0].id").value(category.getId()),
            jsonPath("$[0].name").value("Category 1"),
            jsonPath("$[0].description").value("Description for category 1"),
            jsonPath("$[0].logoUrl").value("https://category1_logo.jpeg"));
  }

  @Test
  void testGetAllCategories_EmptyList() throws Exception {
    categoryRepository.deleteAll();

    mockMvc
        .perform(get("/api/v1/categories"))
        .andExpectAll(status().isOk(), jsonPath("$", hasSize(0)));
  }

  @Test
  void testGetAllCategories_InvalidRequestParams() throws Exception {
    mockMvc
        .perform(get("/api/v1/categories").param("pageSize", "-2").param("pageNumber", "-9"))
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
}
