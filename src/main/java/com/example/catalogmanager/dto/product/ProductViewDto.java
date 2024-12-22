package com.example.catalogmanager.dto.product;

import com.example.catalogmanager.dto.CategoryProductDto;
import java.math.BigDecimal;

public class ProductViewDto {

  private String name;
  private String description;
  private BigDecimal price;
  private int stockQuantity;
  private CategoryProductDto category;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(int stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  public CategoryProductDto getCategory() {
    return category;
  }

  public void setCategory(CategoryProductDto category) {
    this.category = category;
  }
}
