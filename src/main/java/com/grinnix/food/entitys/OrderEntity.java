package com.grinnix.food.entitys;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(nullable = false, length = 80)
  private String ownerName;

  @Column(nullable = false)
  private BigDecimal total;

  @Column(nullable = false, length = 255)
  private String address;

  @Column(nullable = false, length = 255)
  private String observation;

  @OneToMany(
    mappedBy = "order",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<OrderItemEntity> orderItems = new ArrayList<>();

  private Boolean finished = false;

  public void addItem(OrderItemEntity item) {
    orderItems.add(item);
    item.setOrder(this);
  }

  public void removeItem(OrderItemEntity item) {
    orderItems.remove(item);
    item.setOrder(null);
  }

  public BigDecimal calculateTotal() {
    return orderItems
      .stream()
      .map(OrderItemEntity::calculateSubtotal)
      .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public void updateTotal() {
    this.total = calculateTotal();
  }
}
