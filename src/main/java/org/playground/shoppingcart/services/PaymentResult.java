package org.playground.shoppingcart.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.playground.shoppingcart.entities.PaymentStatus;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long orderId;
    private PaymentStatus paymentStatus;
}
