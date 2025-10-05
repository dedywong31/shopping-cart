package org.playground.shoppingcart.services;

import org.playground.shoppingcart.entities.Order;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
}
