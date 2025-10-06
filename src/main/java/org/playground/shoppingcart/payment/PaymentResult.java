package org.playground.shoppingcart.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.playground.shoppingcart.orders.PaymentStatus;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long orderId;
    private PaymentStatus paymentStatus;
}
