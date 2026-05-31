package com.grinnix.food.service;

import com.grinnix.food.entitys.ProductsEntity;
import com.grinnix.food.repositories.ProductsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductsService {

  private final ProductsRepository productsRepository;

  public List<ProductsEntity> findAll() {
    return productsRepository.findAll();
  }

  public ProductsEntity findById(Long id) {
    return productsRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
  }

  public ProductsEntity create(ProductsEntity product) {
    return productsRepository.save(product);
  }

  public ProductsEntity update(Long id, ProductsEntity product) {
    ProductsEntity existingProduct = findById(id);

    existingProduct.setName(product.getName());
    existingProduct.setPrice(product.getPrice());

    return productsRepository.save(existingProduct);
  }

  public void delete(Long id) {
    ProductsEntity product = findById(id);
    productsRepository.delete(product);
  }
}
