package org.playground.shoppingcart.services;

import lombok.AllArgsConstructor;
import org.playground.shoppingcart.dtos.CheckoutRequest;
import org.playground.shoppingcart.dtos.CheckoutResponse;
import org.playground.shoppingcart.entities.Order;
import org.playground.shoppingcart.exceptions.CartEmptyException;
import org.playground.shoppingcart.exceptions.CartNotFoundException;
import org.playground.shoppingcart.repositories.CartRepository;
import org.playground.shoppingcart.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart = cartRepository.getCartWithItemsById(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.getItems().isEmpty()) {
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return new CheckoutResponse(order.getId());
    }
}
