package com.example.catalogmanager.mapper;

import static com.example.catalogmanager.util.ProductTestDataFactory.createProduct;
import static com.example.catalogmanager.util.ProductTestDataFactory.createProductCreateDto;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.catalogmanager.domain.Product;
import com.example.catalogmanager.dto.product.ProductCreateDto;
import com.example.catalogmanager.dto.product.ProductViewDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class ProductMapperUnitTest {
  private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

  @Test
  void toProductViewDto() {
    Product product = createProduct();

    ProductViewDto productViewDto = productMapper.toProductViewDto(product);

    assertThat(productViewDto.getName()).isEqualTo("Test product name");
    assertThat(productViewDto.getDescription()).isEqualTo("Test product description");
    assertThat(productViewDto.getPrice()).isEqualTo(BigDecimal.valueOf(9.99));
    assertThat(productViewDto.getStockQuantity()).isEqualTo(30);
    assertThat(productViewDto.getCategory().getName()).isEqualTo("Test category name");
    assertThat(productViewDto.getCategory().getDescription())
        .isEqualTo("Test category description");
  }

  @Test
  void toProduct() {
    ProductCreateDto productCreateDto = createProductCreateDto();

    Product product = productMapper.toEntity(productCreateDto);

    assertThat(product.getName()).isEqualTo("Test product name");
    assertThat(product.getDescription()).isEqualTo("Test product description");
    assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(9.99));
    assertThat(product.getStockQuantity()).isEqualTo(30);
  }
}
