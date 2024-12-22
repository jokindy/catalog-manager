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
    List<ProductViewDto> products = productService.getAllProducts(pageNumber, pageSize);
    return ResponseEntity.ok(products);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductViewDto> getProductById(@PathVariable Long id) {
    ProductViewDto product = productService.getProductById(id);
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
    List<ProductViewDto> products = productService.searchProductsByName(name, pageNumber, pageSize);
    return ResponseEntity.ok(products);
  }

  @PostMapping
  public ResponseEntity<ProductViewDto> createProduct(
      @Valid @RequestBody ProductCreateDto productCreateDto) {
    ProductViewDto createdProduct = productService.createProduct(productCreateDto);
    return ResponseEntity.ok(createdProduct);
  }

  @PutMapping
  public ResponseEntity<ProductViewDto> updateProduct(
      @Valid @RequestBody ProductUpdateDto productUpdateDto) {
    ProductViewDto updatedProduct = productService.updateProduct(productUpdateDto);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
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
    List<ProductViewDto> products =
        productService.getProductsByCategoryName(categoryName, pageNumber, pageSize);
    return ResponseEntity.ok(products);
  }
}
