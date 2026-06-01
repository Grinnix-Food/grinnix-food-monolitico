package com.grinnix.food.service;

import com.grinnix.food.entitys.OrderEntity;
import com.grinnix.food.entitys.PaymentOrderEntity;
import com.grinnix.food.enums.PaymentStatusEnum;
import com.grinnix.food.repositories.PaymentOrderRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentOrderRepository paymentOrderRepository;
  private final PaymentHookService paymentHookService;
  private final EmailService emailService;

  public void processPayment(String card, Long paymentOrderId) {
    PaymentOrderEntity paymentOrder = paymentOrderRepository
      .findById(paymentOrderId)
      .orElseThrow(() ->
        new RuntimeException("Ordem de pagamaneto inexistente")
      );

    if (paymentOrder.getStatus() == PaymentStatusEnum.PAID) {
      throw new RuntimeException("O pedido já foi pago anteriormente");
    }

    boolean paymentResult = paymentHookService.pay(card, paymentOrder);

    if (!paymentResult) {
      throw new RuntimeException(
        "Erro ao realizar o pagamento, cartão recusado"
      );
    }

    paymentOrder.setStatus(PaymentStatusEnum.PAID);
    paymentOrderRepository.save(paymentOrder);

    String emailBody = createKitchenEmail(paymentOrder);
    emailService.sendEmail(
      "cozinha@grinnix-food.com",
      "Novo pedido aprovado",
      emailBody
    );
  }

  public List<PaymentOrderEntity> listPaymentOrders() {
    return paymentOrderRepository.findAll();
  }

  private String createKitchenEmail(PaymentOrderEntity paymentOrder) {
    OrderEntity order = paymentOrder.getOrder();

    String items = order
      .getOrderItems()
      .stream()
      .map(item ->
        String.format("%dx %s", item.getQuantity(), item.getProduct().getName())
      )
      .collect(Collectors.joining("\n"));

    return String.format(
      """
      Olá, equipe da cozinha,

      Um novo pedido foi aprovado e está pronto para preparação.

      Cliente: %s
      Pedido: #%d
      Endereço de entrega: %s

      Itens do pedido:
      %s

      Valor total: R$ %.2f

      Atenciosamente,
      Sistema Grinnix Food
      """,
      order.getOwnerName(),
      order.getId(),
      order.getAddress(),
      items,
      order.getTotal()
    );
  }
}
