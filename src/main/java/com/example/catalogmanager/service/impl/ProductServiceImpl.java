package com.example.catalogmanager.service.impl;

import com.example.catalogmanager.domain.Category;
import com.example.catalogmanager.domain.Product;
import com.example.catalogmanager.dto.product.ProductCreateDto;
import com.example.catalogmanager.dto.product.ProductUpdateDto;
import com.example.catalogmanager.dto.product.ProductViewDto;
import com.example.catalogmanager.error.exception.NotFoundException;
import com.example.catalogmanager.mapper.ProductMapper;
import com.example.catalogmanager.repository.CategoryRepository;
import com.example.catalogmanager.repository.ProductRepository;
import com.example.catalogmanager.service.ProductService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private final CategoryRepository categoryRepository;

  public ProductServiceImpl(
      ProductRepository productRepository,
      ProductMapper productMapper,
      CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.productMapper = productMapper;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public List<ProductViewDto> getAllProducts(int page, int size) {
    return productRepository.findAll(PageRequest.of(page, size)).getContent().stream()
        .map(productMapper::toProductViewDto)
        .toList();
  }

  @Override
  public ProductViewDto getProductById(Long id) {
    Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
    return productMapper.toProductViewDto(product);
  }

  @Override
  public List<ProductViewDto> searchProductsByName(String name, int page, int size) {
    return productRepository
        .findByName(name, PageRequest.of(page, size))
        .map(productMapper::toProductViewDto)
        .toList();
  }

  @Override
  @Transactional
  public ProductViewDto createProduct(ProductCreateDto productCreateDto) {
    Product product = productMapper.toEntity(productCreateDto);
    Category category =
        categoryRepository
            .findById(productCreateDto.getCategoryId())
            .orElseThrow(NotFoundException::new);

    product.setCategory(category);
    return productMapper.toProductViewDto(productRepository.save(product));
  }

  @Override
  @Transactional
  public ProductViewDto updateProduct(ProductUpdateDto productUpdateDto) {
    Product existingProduct =
        productRepository.findById(productUpdateDto.getId()).orElseThrow(NotFoundException::new);

    existingProduct.setName(productUpdateDto.getName());
    existingProduct.setDescription(productUpdateDto.getDescription());
    existingProduct.setPrice(productUpdateDto.getPrice());
    existingProduct.setStockQuantity(productUpdateDto.getStockQuantity());

    Category category =
        categoryRepository
            .findById(productUpdateDto.getCategoryId())
            .orElseThrow(NotFoundException::new);

    existingProduct.setCategory(category);

    return productMapper.toProductViewDto(productRepository.save(existingProduct));
  }

  @Override
  @Transactional
  public void deleteProduct(Long id) {
    Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
    productRepository.delete(product);
  }

  @Override
  public List<ProductViewDto> getProductsByCategoryName(String categoryName, int page, int size) {
    return productRepository
        .findProductsByCategory_Name(categoryName, PageRequest.of(page, size))
        .map(productMapper::toProductViewDto)
        .toList();
  }
}
