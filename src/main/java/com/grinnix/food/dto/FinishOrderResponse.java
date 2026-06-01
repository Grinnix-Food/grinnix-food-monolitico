package com.grinnix.food.dto;

import java.math.BigDecimal;

public record FinishOrderResponse(
  Long orderId,
  Long paymentOrderId,
  BigDecimal total
) {}
