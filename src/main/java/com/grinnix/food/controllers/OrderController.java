package com.grinnix.food.controllers;

import com.grinnix.food.dto.AddProductToOrderRequest;
import com.grinnix.food.dto.CreateOrderRequest;
import com.grinnix.food.dto.FinishOrderResponse;
import com.grinnix.food.entitys.OrderEntity;
import com.grinnix.food.service.OrderService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping
  public ResponseEntity<List<OrderEntity>> findAll() {
    return ResponseEntity.ok(orderService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderEntity> findById(@PathVariable Long id) {
    return ResponseEntity.ok(orderService.findById(id));
  }

  @PostMapping
  public ResponseEntity<OrderEntity> createOrder(
    @Validated @RequestBody CreateOrderRequest request
  ) {
    OrderEntity order = orderService.createOrder(request);

    return ResponseEntity.created(URI.create("/orders/" + order.getId())).body(
      order
    );
  }

  @PostMapping("/{id}/items")
  public ResponseEntity<OrderEntity> addProduct(
    @PathVariable Long id,
    @Validated @RequestBody AddProductToOrderRequest request
  ) {
    return ResponseEntity.ok(orderService.addProduct(id, request));
  }

  @DeleteMapping("/{id}/items/{itemId}")
  public ResponseEntity<OrderEntity> removeProduct(
    @PathVariable Long id,
    @PathVariable Long itemId
  ) {
    return ResponseEntity.ok(orderService.removeProduct(id, itemId));
  }

  @PostMapping("/{id}/finish")
  public ResponseEntity<FinishOrderResponse> finishOrder(
    @PathVariable Long id
  ) {
    return ResponseEntity.ok(orderService.finishOrder(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrder(id);

    return ResponseEntity.noContent().build();
  }
}
