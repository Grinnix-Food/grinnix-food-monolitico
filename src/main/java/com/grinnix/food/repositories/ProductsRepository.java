package com.grinnix.food.repositories;

import com.grinnix.food.entitys.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository
  extends JpaRepository<ProductsEntity, Long> {}
