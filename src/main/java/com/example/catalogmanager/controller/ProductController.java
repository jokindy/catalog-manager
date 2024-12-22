package com.example.catalogmanager.controller;

import com.example.catalogmanager.dto.product.ProductCreateDto;
import com.example.catalogmanager.dto.product.ProductUpdateDto;
import com.example.catalogmanager.dto.product.ProductViewDto;
import com.example.catalogmanager.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<List<ProductViewDto>> getAllProducts(
      @PositiveOrZero(message = "Must be greater than or equal to 0")
          @RequestParam(defaultValue = "0")
          int pageNumber,
      @Positive(message = "Must be greater than 0") @RequestParam(defaultValue = "10")
          int pageSize) {
    logger.info("Fetching all products with page number: {}, page size: {}", pageNumber, pageSize);
    List<ProductViewDto> products = productService.getAllProducts(pageNumber, pageSize);
    logger.info("Retrieved {} products", products.size());
    return ResponseEntity.ok(products);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductViewDto> getProductById(@PathVariable Long id) {
    logger.info("Fetching product with ID: {}", id);
    ProductViewDto product = productService.getProductById(id);
    logger.info("Retrieved product: {}", product);
    return ResponseEntity.ok(product);
  }

  @GetMapping("/search")
  public ResponseEntity<List<ProductViewDto>> searchProducts(
      @NotBlank(message = "Must be present")
          @Size(min = 3, max = 85, message = "Must be between 3 and 85 characters")
          @RequestParam
          String name,
      @PositiveOrZero(message = "Must be greater than or equal to 0")
          @RequestParam(defaultValue = "0")
          int pageNumber,
      @Positive(message = "Must be greater than 0") @RequestParam(defaultValue = "10")
          int pageSize) {
    logger.info(
        "Searching for products with name: {}, page number: {}, page size: {}",
        name,
        pageNumber,
        pageSize);
    List<ProductViewDto> products = productService.searchProductsByName(name, pageNumber, pageSize);
    logger.info("Found {} products matching the search criteria", products.size());
    return ResponseEntity.ok(products);
  }

  @PostMapping
  public ResponseEntity<ProductViewDto> createProduct(
      @Valid @RequestBody ProductCreateDto productCreateDto) {
    logger.info("Creating new product with details: {}", productCreateDto);
    ProductViewDto createdProduct = productService.createProduct(productCreateDto);
    logger.info("Created product: {}", createdProduct);
    return ResponseEntity.ok(createdProduct);
  }

  @PutMapping
  public ResponseEntity<ProductViewDto> updateProduct(
      @Valid @RequestBody ProductUpdateDto productUpdateDto) {
    logger.info("Updating product with details: {}", productUpdateDto);
    ProductViewDto updatedProduct = productService.updateProduct(productUpdateDto);
    logger.info("Updated product: {}", updatedProduct);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    logger.info("Deleting product with ID: {}", id);
    productService.deleteProduct(id);
    logger.info("Deleted product with ID: {}", id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/category")
  public ResponseEntity<List<ProductViewDto>> getProductsByCategoryName(
      @NotBlank(message = "Must be present")
          @Size(min = 3, max = 85, message = "Must be between 3 and 85 characters")
          @RequestParam
          String categoryName,
      @PositiveOrZero(message = "Must be greater than or equal to 0")
          @RequestParam(defaultValue = "0")
          int pageNumber,
      @Positive(message = "Must be greater than 0") @RequestParam(defaultValue = "10")
          int pageSize) {
    logger.info(
        "Fetching products for category: {}, page number: {}, page size: {}",
        categoryName,
        pageNumber,
        pageSize);
    List<ProductViewDto> products =
        productService.getProductsByCategoryName(categoryName, pageNumber, pageSize);
    logger.info("Retrieved {} products for category: {}", products.size(), categoryName);
    return ResponseEntity.ok(products);
  }
}
