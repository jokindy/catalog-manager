package com.example.catalogmanager.util;

import com.example.catalogmanager.domain.Category;
import com.example.catalogmanager.domain.Product;
import com.example.catalogmanager.dto.product.ProductCreateDto;
import java.math.BigDecimal;

public class ProductTestDataFactory {

  public static Product createProduct() {
    Product product = new Product();
    product.setName("Test product name");
    product.setDescription("Test product description");
    product.setPrice(BigDecimal.valueOf(9.99));
    product.setStockQuantity(30);

    Category category = new Category();

    category.setName("Test category name");
    category.setDescription("Test category description");
    category.setLogoUrl("https://logourl.jpeg");

    product.setCategory(category);

    return product;
  }

  public static ProductCreateDto createProductCreateDto() {
    ProductCreateDto productCreateDto = new ProductCreateDto();

    productCreateDto.setName("Test product name");
    productCreateDto.setDescription("Test product description");
    productCreateDto.setPrice(BigDecimal.valueOf(9.99));
    productCreateDto.setStockQuantity(30);

    return productCreateDto;
  }
}
