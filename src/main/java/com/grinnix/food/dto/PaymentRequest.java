package com.grinnix.food.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PaymentRequest(
  @NotBlank()
  @Size(min = 13, max = 19)
  @Pattern(regexp = "^[0-9]+$")
  String card,

  @NotNull() Long paymentOrderId
) {}
