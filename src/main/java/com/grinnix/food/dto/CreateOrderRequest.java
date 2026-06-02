package com.grinnix.food.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderRequest(
  @NotBlank String ownerName,
  @NotBlank String address,
  @NotBlank String observation
) {}
