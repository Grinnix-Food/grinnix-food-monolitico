package com.grinnix.food.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddProductToOrderRequest(
  @NotNull Long productId,

  @Min(1) Integer quantity
) {}
