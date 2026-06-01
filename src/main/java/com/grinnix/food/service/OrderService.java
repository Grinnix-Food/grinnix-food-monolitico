package com.grinnix.food.service;

import com.grinnix.food.dto.AddProductToOrderRequest;
import com.grinnix.food.dto.CreateOrderRequest;
import com.grinnix.food.dto.FinishOrderResponse;
import com.grinnix.food.entitys.OrderEntity;
import com.grinnix.food.entitys.OrderItemEntity;
import com.grinnix.food.entitys.PaymentOrderEntity;
import com.grinnix.food.entitys.ProductsEntity;
import com.grinnix.food.enums.PaymentStatusEnum;
import com.grinnix.food.repositories.OrderItemRepository;
import com.grinnix.food.repositories.OrderRepository;
import com.grinnix.food.repositories.PaymentOrderRepository;
import com.grinnix.food.repositories.ProductsRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final ProductsRepository productsRepository;
  private final PaymentOrderRepository paymentOrderRepository;

  public List<OrderEntity> findAll() {
    return orderRepository.findAll();
  }

  public OrderEntity findById(Long id) {
    return orderRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
  }

  public OrderEntity createOrder(CreateOrderRequest request) {
    OrderEntity order = new OrderEntity();

    order.setOwnerName(request.ownerName());
    order.setAddress(request.address());
    order.setFinished(false);
    order.setTotal(BigDecimal.ZERO);

    return orderRepository.save(order);
  }

  public OrderEntity addProduct(
    Long orderId,
    AddProductToOrderRequest request
  ) {
    OrderEntity order = findOrder(orderId);

    validateOrderNotFinished(order);

    ProductsEntity product = productsRepository
      .findById(request.productId())
      .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

    OrderItemEntity item = new OrderItemEntity();

    item.setProduct(product);
    item.setQuantity(request.quantity());

    order.addItem(item);
    order.updateTotal();

    return orderRepository.save(order);
  }

  public OrderEntity removeProduct(Long orderId, Long itemId) {
    OrderEntity order = findOrder(orderId);

    validateOrderNotFinished(order);

    OrderItemEntity item = orderItemRepository
      .findById(itemId)
      .orElseThrow(() -> new RuntimeException("Item não encontrado"));

    if (!item.getOrder().getId().equals(orderId)) {
      throw new RuntimeException("Item não pertence ao pedido");
    }

    order.removeItem(item);
    order.updateTotal();

    return orderRepository.save(order);
  }

  public FinishOrderResponse finishOrder(Long orderId) {
    OrderEntity order = findOrder(orderId);

    if (Boolean.TRUE.equals(order.getFinished())) {
      throw new RuntimeException("Pedido já finalizado");
    }

    if (order.getOrderItems().isEmpty()) {
      throw new RuntimeException("Não é possível finalizar um pedido vazio");
    }

    order.updateTotal();

    PaymentOrderEntity paymentOrder = new PaymentOrderEntity();
    
    paymentOrder.setOrder(order);

    paymentOrder.setTotal(order.getTotal());

    paymentOrder.setStatus(PaymentStatusEnum.WAITING_PAYMENT);

    paymentOrder = paymentOrderRepository.save(paymentOrder);

    order.setFinished(true);

    orderRepository.save(order);

    return new FinishOrderResponse(
      order.getId(),
      paymentOrder.getId(),
      order.getTotal()
    );
  }

  public void deleteOrder(Long orderId) {
    OrderEntity order = findOrder(orderId);

    orderRepository.delete(order);
  }

  private OrderEntity findOrder(Long orderId) {
    return orderRepository
      .findById(orderId)
      .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
  }

  private void validateOrderNotFinished(OrderEntity order) {
    if (Boolean.TRUE.equals(order.getFinished())) {
      throw new RuntimeException("Pedido já foi finalizado");
    }
  }
}
