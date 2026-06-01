package com.grinnix.food.repositories;

import com.grinnix.food.entitys.PaymentOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository
    extends JpaRepository<PaymentOrderEntity, Long> {
}