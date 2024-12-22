package com.example.catalogmanager.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class CategoryCreateDto {

  @NotNull(message = "Must not me null")
  @Size(min = 3, max = 85, message = "Must be between 3 and 85 characters")
  private String name;

  @NotNull(message = "Must not me null")
  @Size(min = 3, max = 85, message = "Must be between 3 and 255 characters")
  private String description;

  @NotNull(message = "Must not me null")
  @Size(min = 3, max = 85, message = "Must be between 3 and 85 characters")
  @URL(message = "Must be a valid url")
  private String logoUrl;

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

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }
}
