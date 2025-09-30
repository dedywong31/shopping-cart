package org.playground.shoppingcart.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.playground.shoppingcart.dtos.CheckoutRequest;
import org.playground.shoppingcart.dtos.CheckoutResponse;
import org.playground.shoppingcart.dtos.ErrorDto;
import org.playground.shoppingcart.entities.Order;
import org.playground.shoppingcart.entities.OrderItem;
import org.playground.shoppingcart.entities.OrderStatus;
import org.playground.shoppingcart.repositories.CartRepository;
import org.playground.shoppingcart.repositories.OrderRepository;
import org.playground.shoppingcart.services.AuthService;
import org.playground.shoppingcart.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    @PostMapping("/")
    public ResponseEntity<?> checkout(
        @Valid @RequestBody CheckoutRequest checkoutRequest
    ) {
        var cart = cartRepository.getCartWithItemsById(checkoutRequest.getCartId()).orElse(null);
        if (cart == null) {
            return ResponseEntity.badRequest().body(
                new ErrorDto("Cart not found")
            );
        }

        if (cart.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body(
                new ErrorDto("Cart is empty")
            );
        }

        var order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(authService.getCurrentUser());

        cart.getItems().forEach(item -> {
            var orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setTotalPrice(item.getTotalPrice());
            order.getItems().add(orderItem);
        });

        orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return ResponseEntity.ok(new CheckoutResponse(order.getId()));
    }
}
