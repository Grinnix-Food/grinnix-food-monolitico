package com.grinnix.food.controllers;

import com.grinnix.food.entitys.ProductsEntity;
import com.grinnix.food.service.ProductsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

  private final ProductsService productsService;

  @GetMapping
  public ResponseEntity<List<ProductsEntity>> findAll() {
    return ResponseEntity.ok(productsService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductsEntity> findById(@PathVariable Long id) {
    return ResponseEntity.ok(productsService.findById(id));
  }

  @PostMapping
  public ResponseEntity<ProductsEntity> create(
    @RequestBody ProductsEntity product
  ) {
    ProductsEntity createdProduct = productsService.create(product);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductsEntity> update(
    @PathVariable Long id,
    @RequestBody ProductsEntity product
  ) {
    return ResponseEntity.ok(productsService.update(id, product));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    productsService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
