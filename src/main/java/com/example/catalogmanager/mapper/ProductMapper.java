package com.example.catalogmanager.mapper;

import com.example.catalogmanager.domain.Product;
import com.example.catalogmanager.dto.product.ProductCreateDto;
import com.example.catalogmanager.dto.product.ProductViewDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

  public abstract ProductViewDto toProductViewDto(Product product);

  public abstract Product toEntity(ProductCreateDto productCreateDto);
}
