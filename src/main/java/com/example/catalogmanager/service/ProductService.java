package com.example.catalogmanager.service;

import com.example.catalogmanager.dto.product.ProductCreateDto;
import com.example.catalogmanager.dto.product.ProductUpdateDto;
import com.example.catalogmanager.dto.product.ProductViewDto;
import java.util.List;

public interface ProductService {
  List<ProductViewDto> getAllProducts(int page, int size);

  ProductViewDto getProductById(Long id);

  List<ProductViewDto> searchProductsByName(String name, int page, int size);

  ProductViewDto createProduct(ProductCreateDto productCreateDto);

  ProductViewDto updateProduct(ProductUpdateDto productViewDto);

  void deleteProduct(Long id);

  List<ProductViewDto> getProductsByCategoryName(String categoryName, int page, int size);
}
