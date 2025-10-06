package org.playground.shoppingcart.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CheckoutSession {
    private String checkoutUrl;
}
