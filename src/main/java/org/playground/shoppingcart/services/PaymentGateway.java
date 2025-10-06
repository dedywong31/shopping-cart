package org.playground.shoppingcart.services;

import org.playground.shoppingcart.dtos.WebhookRequest;
import org.playground.shoppingcart.entities.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
