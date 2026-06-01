package com.grinnix.food.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grinnix.food.enums.PaymentStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentOrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private PaymentStatusEnum status;

  @Column()
  private BigDecimal total;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orders_id", nullable = false)
  @JsonIgnore
  private OrderEntity order;
}
