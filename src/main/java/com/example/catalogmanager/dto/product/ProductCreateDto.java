package com.example.catalogmanager.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductCreateDto {

  @NotNull(message = "Must not me null")
  @Size(min = 3, max = 85, message = "Must be between 3 and 85 characters")
  private String name;

  @NotNull(message = "Must not me null")
  @Size(min = 3, max = 255, message = "Must be between 3 and 255 characters")
  private String description;

  @NotNull(message = "Must not me null")
  @Positive(message = "Must be positive")
  private BigDecimal price;

  @Positive(message = "Must be positive")
  private int stockQuantity;

  @NotNull(message = "Must not me null")
  private Long categoryId;

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

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }
}