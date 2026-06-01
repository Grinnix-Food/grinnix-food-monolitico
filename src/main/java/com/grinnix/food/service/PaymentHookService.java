package com.grinnix.food.service;

import com.grinnix.food.entitys.PaymentOrderEntity;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class PaymentHookService {

  public boolean pay(String card, PaymentOrderEntity order) {
    Integer lastNumberOfCard = Integer.parseInt(
      card.substring(card.length() - 1)
    );
    Integer randomSeed = lastNumberOfCard;
    Random generate = new Random(randomSeed);

    return generate.nextBoolean();
  }
}
